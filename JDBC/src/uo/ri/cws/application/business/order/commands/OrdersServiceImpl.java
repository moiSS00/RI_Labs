package uo.ri.cws.application.business.order.commands;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrdersService;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class OrdersServiceImpl implements OrdersService {

    private CommandExecutor executor = new CommandExecutor();

    @Override
    public List<OrderDto> generateOrders() throws BusinessException {
	GenerateOrders go = new GenerateOrders();
	return executor.execute(go);
    }

    @Override
    public List<OrderDto> findByProviderNif(String nif) throws BusinessException {
	ListByProvider lp = new ListByProvider(nif);
	return executor.execute(lp);
    }

    @Override
    public Optional<OrderDto> findByCode(String code) throws BusinessException {
	ViewOrderDetail vod = new ViewOrderDetail(code);
	return executor.execute(vod);
    }

    @Override
    public OrderDto receive(String code) throws BusinessException {
	// TODO Auto-generated method stub
	return null;
    }

}
