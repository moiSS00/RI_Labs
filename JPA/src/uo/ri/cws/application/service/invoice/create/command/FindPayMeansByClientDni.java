package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindPayMeansByClientDni implements Command<List<PaymentMeanDto>> {

	private PaymentMeanRepository repo = Factory.repository.forPaymentMean();
	private String dni;

	public FindPayMeansByClientDni(String dni) {
		ArgumentChecks.isNotEmpty(dni);
		this.dni = dni;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		return DtoAssembler.toPaymentMeanDtoList(repo.findPaymentMeansByClientDni(dni));
	}
}
