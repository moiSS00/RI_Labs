package uo.ri.cws.application.business.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.create.commands.WorkOrdersBilling;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class InvoicingServiceImpl implements InvoicingService {

    CommandExecutor executor = new CommandExecutor();

    @Override
    public InvoiceDto createInvoiceFor(List<String> workOrderIds) throws BusinessException {
	WorkOrdersBilling wob = new WorkOrdersBilling(workOrderIds);
	return executor.execute(wob);
    }

    @Override
    public Optional<InvoiceDto> findInvoice(Long number) throws BusinessException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void settleInvoice(String invoiceId, Map<Long, Double> charges) throws BusinessException {
	// TODO Auto-generated method stub

    }

}
