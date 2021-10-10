package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Supply extends BaseEntity {

	private double price;
	private int deliveryTerm;

	private Provider provider;
	private SparePart sparePart;

	public Supply() {

	}

	public Supply(Provider provider, SparePart sparePart, double price, int deliveryTerm) {
		ArgumentChecks.isNotNull(provider);
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isTrue(price >= 0);
		ArgumentChecks.isTrue(deliveryTerm >= 0);
		this.provider = provider;
		this.sparePart = sparePart;
		this.price = price;
		this.deliveryTerm = deliveryTerm;
		Associations.SupplyAssociation.link(sparePart, provider, this);
	}

	public double getPrice() {
		return price;
	}

	public int getDeliveryTerm() {
		return deliveryTerm;
	}

	public Provider getProvider() {
		return provider;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public void setPrice(double price) {
		ArgumentChecks.isTrue(price >= 0);
		this.price = price;
	}

	public void setDeliveryTerm(int deliveryTerm) {
		ArgumentChecks.isTrue(deliveryTerm >= 0);
		this.deliveryTerm = deliveryTerm;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((sparePart == null) ? 0 : sparePart.hashCode());
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
		Supply other = (Supply) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (sparePart == null) {
			if (other.sparePart != null)
				return false;
		} else if (!sparePart.equals(other.sparePart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Supply [price=" + price + ", deliveryTerm=" + deliveryTerm + "]";
	}
	
	public void increasePriceBy(double percent) {
		price = price * (1 + (percent / 100)); 
	}

}
