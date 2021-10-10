package uo.ri.cws.application.service.invoice.create.command;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ChargeRepository;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.PaymentMean;

public class SettleInvoice implements Command<Void> {

	private InvoiceRepository repoI = Factory.repository.forInvoice();
	private PaymentMeanRepository repoP = Factory.repository.forPaymentMean();
	private ChargeRepository repoC = Factory.repository.forCharge();
	private String invoiceId;
	private Map<String, Double> charges;

	public SettleInvoice(String invoiceId, Map<String, Double> charges) {
		ArgumentChecks.isNotEmpty(invoiceId);
		ArgumentChecks.isNotNull(charges);
		this.invoiceId = invoiceId;
		this.charges = charges;
	}

	@Override
	public Void execute() throws BusinessException {
		validateInvoice();
		validateCharges();

		Invoice invoice = repoI.findById(invoiceId).get();

		for (Entry<String, Double> entry : charges.entrySet()) {
			repoC.add(new Charge(invoice, repoP.findById(entry.getKey()).get(), entry.getValue()));
		}

		invoice.settle();
		return null;
	}

	
	/**
	 * Comprueba si la factura es correcta 
	 * @throws BusinessException
	 */
	private void validateInvoice() throws BusinessException {

		// Comprobamos si existe la factura
		Optional<Invoice> invoiceOp = repoI.findById(invoiceId);
		BusinessChecks.isTrue(invoiceOp.isPresent(), "The invoice does not exist");

		// Comprovamos si la factura no esta pagada
		Invoice invoice = invoiceOp.get();
		BusinessChecks.isTrue(invoice.isNotSettled(), "The invoice is already paid");
	}

	
	/**
	 * Comprueba si los metodos de pago son correctos 
	 * @throws BusinessException
	 */
	private void validateCharges() throws BusinessException {

		// Comprobamos que existan los metodos de pago y que puedan pagar su cantidad
		double total = 0;
		for (Entry<String, Double> entry : charges.entrySet()) {
			Optional<PaymentMean> pm = repoP.findById(entry.getKey());
			BusinessChecks.isTrue(pm.isPresent(), "The paymentMean " + entry.getKey() + "does not exist");
			BusinessChecks.isTrue(pm.get().canPay(entry.getValue()),"The payment means cannot be used to pay the specified amount");
			total += entry.getValue();
		}

		// Comprobamos la diferencia
		Invoice invoice = repoI.findById(invoiceId).get();
		BusinessChecks.isTrue(Math.abs(total - invoice.getAmount()) <= 0.01,
				"the addition of all amounts charged to each payment mean does not equals the amount of the invoice with a precision of two cents");

	}

}
