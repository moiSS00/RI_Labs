package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Order extends BaseEntity {

	public enum OrderStatus {
		PENDING, RECEIVED
	}

	private String code;

	private double amount = 0.0;
	private LocalDate orderedDate;
	private LocalDate receptionDate;

	private OrderStatus status = OrderStatus.PENDING;

	private Provider provider;

	private Set<OrderLine> orderLines = new HashSet<OrderLine>();

	public Order() {

	}

	public Order(String code) {
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
		this.orderedDate = LocalDate.now();
	}

	public String getCode() {
		return code;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public LocalDate getReceptionDate() {
		return receptionDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public Provider getProvider() {
		return provider;
	}

	public Set<OrderLine> getOrderLines() {
		return new HashSet<OrderLine>(orderLines);
	}

	Set<OrderLine> _getOrderLines() {
		return orderLines;
	}

	void _setProvider(Provider provider) {
		this.provider = provider;
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
		Order other = (Order) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [code=" + code + ", amount=" + amount + ", orderedDate=" + orderedDate + ", receptionDate="
				+ receptionDate + ", status=" + status + "]";
	}

	/**
	 * Añade una nueva orderLine a partir de un Supply y incrementa el precio del
	 * pedido
	 * 
	 * @param supply Supply base
	 */
	public void addSparePartFromSupply(Supply supply) {
		ArgumentChecks.isNotNull(supply);
		SparePart sparePart = supply.getSparePart();
		checkRepeated(sparePart);
		if (sparePart.getStock() < sparePart.getMinStock()) {
			OrderLine ol = new OrderLine(supply.getSparePart(), supply.getPrice());
			_getOrderLines().add(ol);
		}
		computeAmount();
	}

	/**
	 * Calcula el precio del pedido
	 */
	private void computeAmount() {
		amount = 0.0;
		for (OrderLine ol : orderLines) {
			amount += ol.getAmount();
		}
	}

	/**
	 * Comprueba si una sparePart ya esta contenida en las orderLines
	 * 
	 * @param sparePart Sparepart a analizar
	 */
	private void checkRepeated(SparePart sparePart) {
		Set<OrderLine> orderLines = getOrderLines();
		for (OrderLine ol : orderLines) {
			StateChecks.isFalse(ol.getSparePart().equals(sparePart));
		}
	}

	/**
	 * Se recibe el pedido y se actualiza el estado
	 */
	public void receive() {
		StateChecks.isFalse(isReceived());
		status = OrderStatus.RECEIVED;
		for (OrderLine ol : orderLines) {
			ol.receive();
		}
		receptionDate = LocalDate.now();
	}

	public boolean isReceived() {
		return status.equals(OrderStatus.RECEIVED);
	}

	public boolean isPending() {
		return status.equals(OrderStatus.PENDING);
	}

	/**
	 * Elimina un repuesto de las orderLines (elimando la orderLine de ese repuesto)
	 * 
	 * @param spare Repuesto a eliminar
	 */
	public void removeSparePart(SparePart spare) {
		ArgumentChecks.isNotNull(spare);
		OrderLine aux = null;
		Set<OrderLine> orderLines = getOrderLines();
		for (OrderLine ol : orderLines) {
			if (ol.getSparePart().equals(spare)) {
				aux = ol;
			}
		}
		_getOrderLines().remove(aux);
		computeAmount();
	}

}
