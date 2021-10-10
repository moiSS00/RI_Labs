package uo.ri.cws.application.business.order.commands;

import java.sql.SQLException;
import java.util.List;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderRecord;

public class ListByProvider implements Command<List<OrderDto>> {

    private String nif;

    public ListByProvider(String nif) {
	this.nif = nif;
    }

    public List<OrderDto> execute() throws SQLException, BusinessException {
	ProviderGateway pg = PersistenceFactory.forProvider();
	OrderGateway og = PersistenceFactory.forOrder();
	validateData();
	BusinessCheck.isFalse(pg.findByNIF(nif).isEmpty(), "The provider does not exist");

	ProviderRecord provider = pg.findByNIF(nif).get();
	List<OrderRecord> orders = og.findByProviderId(provider.id);
	List<OrderDto> ordersDto = DtoMapper.toDtoListOrders(orders);
	for (OrderDto order : ordersDto) {
	    order.provider.name = provider.name;
	    order.provider.nif = provider.nif;
	}
	return ordersDto;

    }

    /*
     * Valida el nif que se ha introducido
     */
    private void validateData() {
	Argument.isNotEmpty(nif, "NIF can not be empty");
    }

}
