package uo.ri.cws.domain;

import java.time.LocalDate;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {

	private String number;
	private String type;
	private LocalDate validThru;

	CreditCard() {
	}

	public CreditCard(String number) {
		this(number, "creditcard-type", LocalDate.now());
	}

	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotEmpty(number);
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	@Override
	public void pay(double amount) {
		StateChecks.isTrue(LocalDate.now().compareTo(getValidThru()) < 0);
		super.pay(amount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		CreditCard other = (CreditCard) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type + ", validThru=" + validThru + "]";
	}

	@Override
	public boolean canPay(double amount) {
		return LocalDate.now().compareTo(validThru) < 0;
	}

}
