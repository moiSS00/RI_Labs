package uo.ri.cws.application.business.order.commands;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderLineDto;
import uo.ri.cws.application.business.order.OrderDto.OrderedProviderDto;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class ViewOrderDetail implements Command<Optional<OrderDto>> {

    private String code;

    public ViewOrderDetail(String code) {
	this.code = code;
    }

    public Optional<OrderDto> execute() throws SQLException, BusinessException {
	validateData();

	OrderGateway og = PersistenceFactory.forOrder();
	OrderLineGateway olg = PersistenceFactory.forOrderLine();
	ProviderGateway pg = PersistenceFactory.forProvider();

	BusinessCheck.isFalse(og.findByCode(code).isEmpty(), "The provider does not exist");

	Optional<OrderRecord> orderRecord = og.findByCode(code);
	List<OrderLineRecord> orderLines = olg.findByOrderId(orderRecord.get().id);
	Optional<ProviderRecord> provider = pg.findById(orderRecord.get().providerId);

	Optional<OrderDto> result = orderRecord.isEmpty() ? Optional.ofNullable(null)
		: Optional.ofNullable(createOrderDto(orderRecord.get(), orderLines, provider.get()));

	return result;

    }

    /*
     * Crea un OrderDto a partir de un orderRecord conteniendo la info basica del
     * pedido La lista de order lines que forman al pedido El proveedor del pedido
     */
    private OrderDto createOrderDto(OrderRecord orderRecord, List<OrderLineRecord> orderLines, ProviderRecord provider)
	    throws SQLException {

	SparePartGateway spg = PersistenceFactory.forSparePart();

	OrderDto order = DtoMapper.toDto(orderRecord);

	OrderedProviderDto p = new OrderedProviderDto();
	p.id = provider.id;
	p.name = provider.name;
	p.nif = provider.nif;
	order.provider = p;

	for (OrderLineRecord l : orderLines) {
	    OrderLineDto line = new OrderLineDto();
	    SparePartDto sparePartUsed = DtoMapper.toDto(spg.findById(l.sparePart_id).get());
	    line.sparePart.id = sparePartUsed.id;
	    line.sparePart.code = sparePartUsed.code;
	    line.sparePart.description = sparePartUsed.description;
	    line.quantity = l.quantity;
	    line.price = l.price;

	    order.lines.add(line);

	}

	return order;
    }

    /*
     * Valida el codigo introducido
     */
    private void validateData() {
	Argument.isNotEmpty(code, "The code can not be empty");
    }

}
