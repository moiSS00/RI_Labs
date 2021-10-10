package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;

public class OrderLine {

	private double price;
	private int quantity;

	private SparePart sparePart;

	public OrderLine() {

	}

	public OrderLine(SparePart sparePart, double price) {
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isTrue(sparePart.getStock() < sparePart.getMinStock());
		ArgumentChecks.isTrue(price >= 0);
		this.price = price;
		this.quantity = sparePart.getQuantityToOrder();
		this.sparePart = sparePart;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	/**
	 * Actualiza el stock y el precio del repuesto al recivir el pedido
	 */
	public void receive() {
		sparePart.updatePriceAndStock(price, quantity);
	}

	/**
	 * Calcula el precio de la orderLine
	 * 
	 * @return Precio de la orderLine
	 */
	public double getAmount() {
		return price * quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((sparePart == null) ? 0 : sparePart.hashCode());
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
		OrderLine other = (OrderLine) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
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
		return "OrderLine [price=" + price + "]";
	}

}
