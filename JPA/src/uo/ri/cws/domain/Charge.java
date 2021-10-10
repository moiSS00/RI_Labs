package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

public class Charge extends BaseEntity {

	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	private Invoice invoice;
	private PaymentMean paymentMean;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		// link invoice, this and paymentMean
		ArgumentChecks.isNotNull(amount);
		ArgumentChecks.isNotNull(invoice);
		ArgumentChecks.isNotNull(paymentMean);

		this.amount = amount;
		paymentMean.pay(amount);
		Associations.Charges.link(paymentMean, this, invoice);
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlinks invoice, this and paymentMean
		StateChecks.isTrue(getInvoice().getStatus() != InvoiceStatus.PAID);
		paymentMean.pay(-amount);
		Associations.Charges.unlink(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((paymentMean == null) ? 0 : paymentMean.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charge other = (Charge) obj;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (paymentMean == null) {
			if (other.paymentMean != null)
				return false;
		} else if (!paymentMean.equals(other.paymentMean))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + "]";
	}

}
