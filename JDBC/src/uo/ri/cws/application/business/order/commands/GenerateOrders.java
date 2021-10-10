package uo.ri.cws.application.business.order.commands;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderLineDto;
import uo.ri.cws.application.business.provider.ProviderDto;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway;

public class GenerateOrders implements Command<List<OrderDto>> {

    public List<OrderDto> execute() throws SQLException {

	// Creamos las gateways necesarias
	SparePartGateway spg = PersistenceFactory.forSparePart();
	SupplyGateway sg = PersistenceFactory.forSupply();
	ProviderGateway pg = PersistenceFactory.forProvider();

	// Obtenemos las piezas de las que hay que realizar un pedido, controlando que
	// no hayan sido pedidas
	// con anteriodidad
	List<String> sparepartsForOrderIds = spg.getNotOrderedSpareParts();
	List<SparePartDto> sparepartsForOrder = new ArrayList<SparePartDto>();
	for (String id : sparepartsForOrderIds) {
	    SparePartDto sparePart = DtoMapper.toDto(spg.findById(id).get());
	    sparepartsForOrder.add(sparePart);
	}

	// Creamos un hashMap que representara al cojunto de pedidos
	HashMap<String, List<String>> pedidos = new HashMap<String, List<String>>();

	// Recorremos los repuestos, añadiendolos a los pedidos, segun varios criterios
	// aplicados en orden
	for (SparePartDto sparePart : sparepartsForOrder) {
	    bestProvider(sparePart.id, pedidos);
	}

	// Creamos los pedidos
	List<OrderDto> pedidosList = new ArrayList<OrderDto>();
	for (Entry<String, List<String>> entry : pedidos.entrySet()) {

	    // Informacion general del pedido
	    OrderDto order = createDefaultOrder();

	    // Se crean y asignan las orderLine/s necesarias al pedido
	    double amount = 0;
	    for (String id : entry.getValue()) {
		OrderLineDto line = new OrderLineDto();
		SparePartDto sparePartUsed = DtoMapper.toDto(spg.findById(id).get());
		int quantity = sparePartUsed.maxStock - sparePartUsed.stock;
		Double price = sg.getPriceByProviderAndSpare(entry.getKey(), id);
		amount += quantity * price;
		line.sparePart.id = sparePartUsed.id;
		line.sparePart.code = sparePartUsed.code;
		line.sparePart.description = sparePartUsed.description;
		line.quantity = quantity;
		line.price = price;

		order.lines.add(line);
	    }

	    // Se crea y asigna el proveedor al pedido
	    ProviderDto provider = DtoMapper.toDto(pg.findById(entry.getKey()).get());
	    order.provider.id = provider.id;
	    order.provider.nif = provider.nif;
	    order.provider.name = provider.name;
	    order.amount = amount;
	    amount = 0.0;

	    // Se añaden los pedidos y las orderLines a la base de datos y a la lista de
	    // pedidos
	    pedidosList.add(order);
	    updateDatabase(order);

	}

	return pedidosList;
    }

    /*
     * Se añaden los registros oportunos en la tabla orders y en la tabla orderlines
     */
    private void updateDatabase(OrderDto order) throws SQLException {

	OrderLineGateway olg = PersistenceFactory.forOrderLine();
	OrderGateway og = PersistenceFactory.forOrder();

	og.add(DtoMapper.toRecord(order));

	for (OrderLineDto line : order.lines) {
	    olg.add(DtoMapper.toRecord(line, order.id));
	}

    }

    /*
     * A partir del hashmap que representa los pedidos, el id del proveedor que hara
     * el pedido y el id de la pieza de la que se hara el pedido, se procede a
     * realizar las respectivas operaciones de añadir pedido con el hashmap
     */
    private void addPedido(String idProvider, String sparePartId, HashMap<String, List<String>> pedidos) {
	if (pedidos.containsKey(idProvider)) {
	    pedidos.get(idProvider).add(sparePartId);
	} else {
	    List<String> aux = new ArrayList<String>();
	    aux.add(sparePartId);
	    pedidos.put(idProvider, aux);
	}
    }

    /*
     * Este metodo asigna un repuestos al mejor proveedor segun varios criterios
     * aplicados por orden. Se pasan como parametros el id del repuestio a
     * introducir en mis pedidos y el hashmap que representa los pedidos. Se
     * controla tambien que al proveedor que se le asingara el pedido no tenga otro
     * en esta PENDING
     */
    private void bestProvider(String sparepartId, HashMap<String, List<String>> pedidos) throws SQLException {
	SupplyGateway sg = PersistenceFactory.forSupply();

	// Primer criterio, por menor precio
	List<String> supplyPrice = sg.getMinimunPriceSupply(sparepartId);

	if (supplyPrice.size() > 1) {
	    // Segundo criterio, por menor delivery
	    List<String> supplyDelivery = sg.getMinimunDeliverSupply(sparepartId);
	    if (supplyDelivery.size() > 1) {
		// Tercer criterio, por orden lexicografico
		String supplyInOrder = sg.getFirstSupplyInOrder(sparepartId);
		addPedido(supplyInOrder, sparepartId, pedidos);
	    }

	    else {
		addPedido(supplyDelivery.get(0), sparepartId, pedidos);
	    }
	}

	else if (!supplyPrice.isEmpty()) { // Tener cuidado por si no hay proveedores que provean una pieza
	    addPedido(supplyPrice.get(0), sparepartId, pedidos);
	}
    }

    /*
     * Crea un pedido desde cero con la informacion basica
     */
    private OrderDto createDefaultOrder() {
	OrderDto order = new OrderDto();
	order.id = UUID.randomUUID().toString();
	order.code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
	order.version = 1;
	order.orderedDate = LocalDate.now();
	order.status = "PENDING";
	return order;
    }

}
