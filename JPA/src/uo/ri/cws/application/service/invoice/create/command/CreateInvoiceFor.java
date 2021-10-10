package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {

		checkWorkOrdersExists(workOrderIds);
		checkWorkOrdersFnished(workOrderIds);

		Long number = invsRepo.getNextInvoiceNumber();
		List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);
		Invoice invoice = new Invoice(number, workOrders);

		invsRepo.add(invoice);

		return DtoAssembler.toDto(invoice);
	}

	/**
	 * Comprueba si todas las workOrders existen
	 * 
	 * @param workOrderIds Lista de las wokorders a comproar identificadas por su id
	 * @throws BusinessException Si alguna de las workOrders no existen
	 */
	public void checkWorkOrdersExists(List<String> workOrderIds) throws BusinessException {

		BusinessChecks.isTrue(wrkrsRepo.findByIds(workOrderIds).size() == workOrderIds.size(),
				"Workorder does not exist");
	}

	/**
	 * Comprueba si todas las workOrders estan en estado FINISHED
	 * 
	 * @param workOrderIds Lista de las workorders identificadas por su id
	 * @throws BusinessException Si alguna de las workOrders no esta en estado
	 *                           FINISHED
	 */
	public void checkWorkOrdersFnished(List<String> workOrderIds) throws BusinessException {
		List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);

		for (WorkOrder w : workOrders) {
			BusinessChecks.isTrue(w.isFinished(), "Workorder is not finished yet");
		}
	}

}
