package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindWorOrdersByPlate implements Command<List<InvoicingWorkOrderDto>> {

	private WorkOrderRepository repoW = Factory.repository.forWorkOrder();
	private String plate;

	public FindWorOrdersByPlate(String plate) {
		ArgumentChecks.isNotEmpty(plate);
		this.plate = plate;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		return DtoAssembler.toInvoicingWorkOrderDtoList(repoW.findByPlateNumber(plate));
	}

}
