package uo.ri.cws.application.service.invoice.create.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>>{

	private InvoiceRepository repo = Factory.repository.forInvoice(); 
	private Long number; 
	
	public FindInvoiceByNumber(Long number) {
		ArgumentChecks.isNotNull(number);
		this.number = number; 
	}
	
	@Override
	public Optional<InvoiceDto> execute() throws BusinessException {
		return repo.findByNumber(number).map(i -> DtoAssembler.toDto(i)); 
	}

}
