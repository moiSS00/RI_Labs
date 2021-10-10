package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class Voucher extends PaymentMean {

	private String code;
	private double available = 0.0;
	private String description;

	Voucher() {
	}

	public Voucher(String code) {
		this(code, "voucher-description", 0);
	}

	public Voucher(String code, String descripcion, double available) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(descripcion);
		this.code = code;
		this.description = descripcion;
		this.available = available;
	}

	public String getCode() {
		return code;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		StateChecks.isFalse(available < amount);
		super.pay(amount);
		available -= amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
	}

	@Override
	public boolean canPay(double amount) {
		return amount <= available;
	}

}
