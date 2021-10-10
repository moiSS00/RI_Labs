package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TSPAREPARTS")
public class SparePart extends BaseEntity {

	// natural attributes
	@Column(unique = true)
	@Basic(optional = false)
	private String code;

	@Basic(optional = false)
	private String description;

	@Basic(optional = false)
	private double price;

	private int stock;
	private int minStock;
	private int maxStock;

	// accidental attributes
	@OneToMany(mappedBy = "sparePart")
	private Set<Substitution> substitutions = new HashSet<>();

	@OneToMany(mappedBy = "sparePart")
	private Set<Supply> supplies = new HashSet<>();

	SparePart() {
	}

	public SparePart(String code) {
		this(code, "sparepart-description", 0, 0, 0, 0);
	}

	public SparePart(String code, String description, double price) {
		this(code, description, price, 0, 0, 0);
	}

	public SparePart(String code, String description, double price, int stock, int minStock, int maxStock) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(price >= 0);
		ArgumentChecks.isTrue(stock >= 0);
		ArgumentChecks.isTrue(minStock >= 0);
		ArgumentChecks.isTrue(maxStock >= 0);
		this.code = code;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.minStock = minStock;
		this.maxStock = maxStock;
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public int getMinStock() {
		return minStock;
	}

	public int getMaxStock() {
		return maxStock;
	}

	public Set<Supply> getSupplies() {
		return new HashSet<Supply>(supplies);
	}

	Set<Supply> _getSupplies() {
		return supplies;
	}

	public void setDescription(String description) {
		ArgumentChecks.isNotEmpty(description);
		this.description = description;
	}

	public void setPrice(double price) {
		ArgumentChecks.isTrue(price >= 0);
		this.price = price;
	}

	public void setStock(int stock) {
		ArgumentChecks.isTrue(stock >= 0);
		this.stock = stock;
	}

	public void setMinStock(int minStock) {
		ArgumentChecks.isTrue(stock >= 0);
		this.minStock = minStock;
	}

	public void setMaxStock(int maxStock) {
		ArgumentChecks.isTrue(stock >= 0);
		this.maxStock = maxStock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		SparePart other = (SparePart) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + ", stock=" + stock
				+ ", minStock=" + minStock + ", maxStock=" + maxStock + "]";
	}

	/**
	 * Calcula el numero de unidades de un repuesto que se deben pedir
	 * 
	 * @return El numero de uds a pedir, devolvera 0 si el stock actual no es menor
	 *         que el stock minimo
	 */
	public int getQuantityToOrder() {
		int uds = 0;
		if (stock < minStock) {
			uds = maxStock - stock;
		}
		return uds;
	}

	/**
	 * Devuelve el numero de unidades vendidas
	 * 
	 * @return Numero de unidades vendidas
	 */
	public int getTotalUnitsSold() {
		int total = 0;
		Set<Substitution> substitutions = _getSubstitutions();
		for (Substitution s : substitutions) {
			total += s.getQuantity();
		}
		return total;
	}

	/**
	 * Actualiza el campo stock y price del repuesto al recibir un pedido de este
	 * 
	 * @param purchasePrice Precio de compra
	 * @param newQuantity   Nueva cantidad
	 * @throws IllegalArgumentException si el precio es negativo o la cantidad es <=
	 *                                  0.
	 */
	public void updatePriceAndStock(double purchasePrice, int newQuantity) {
		ArgumentChecks.isTrue(purchasePrice >= 0);
		ArgumentChecks.isTrue(newQuantity > 0);
		int expectedStock = stock + newQuantity;
		double expectedPrice = (stock * price + newQuantity * purchasePrice * 1.2) / expectedStock;
		stock = expectedStock;
		price = expectedPrice;
	}

	/**
	 * Obtiene el mejor Supply para el repuesto
	 * 
	 * @return Mejor Supply/suministro para el repuesto
	 */
	public Optional<Supply> getBestSupply() {

		// Inicializamos variables necesarias
		Set<Supply> supplies = getSupplies();
		Set<Supply> bestSuppliesPrice = new HashSet<Supply>();
		Set<Supply> bestSuppliesDeliveyTerm = new HashSet<Supply>();

		// Primero filtramos por precio
		double minPrice = minPrice();
		for (Supply s : supplies) {
			if (s.getPrice() == minPrice) {
				bestSuppliesPrice.add(s);
			}
		}

		// Si se encuentra a mas de uno, se filtrara por deliveryTerm
		if (bestSuppliesPrice.size() > 1) {
			int minDeliveryTerm = minDeliveryTerm(bestSuppliesPrice);
			for (Supply s : bestSuppliesPrice) {
				if (s.getDeliveryTerm() == minDeliveryTerm) {
					bestSuppliesDeliveyTerm.add(s);
				}
			}

			// Si se encuentra a mas de uno, se filtrar por orden de nif del proveedor
			if (bestSuppliesDeliveyTerm.size() > 1) {
				String nif = bestSuppliesDeliveyTerm.stream().findFirst().get().getProvider().getId();
				Optional<Supply> bestSupply = null;
				for (Supply s : bestSuppliesDeliveyTerm) {
					if (s.getProvider().getNif().compareTo(nif) < 0) {
						nif = s.getProvider().getNif();
						bestSupply = Optional.of(s);
					}
				}

				// Devuelve el primero en orden lexicografico por NIF del proveedor
				return bestSupply;
			}

			// Si solo hay uno con el menor plazo de entrega, se devuelve
			return bestSuppliesDeliveyTerm.stream().findFirst();
		}

		// Si solo hay uno con el precio más barato, se devuelve
		return bestSuppliesPrice.stream().findFirst();
	}

	/**
	 * Devuelve el precio minimo de los supplies de esta sparepart.
	 * 
	 * @return Precio minimo de entre todos los supplies
	 */
	private double minPrice() {
		Set<Supply> supplies = getSupplies();
		if (supplies.isEmpty()) {
			return 0;
		}
		double min = supplies.stream().findFirst().get().getPrice();
		for (Supply s : supplies) {
			if (s.getPrice() < min) {
				min = s.getPrice();
			}
		}
		return min;
	}

	/**
	 * Devuelve el menor plazo de entrega, de una lista de supplies de este repuesto
	 * 
	 * @param bestSupplies Lista de supplies del repuesto
	 * @return Menor tiempo de espera entre los supplies de la lista
	 */
	private int minDeliveryTerm(Set<Supply> bestSupplies) {
		int min = bestSupplies.stream().findFirst().get().getDeliveryTerm();
		for (Supply s : bestSupplies) {
			if (s.getDeliveryTerm() < min) {
				min = s.getDeliveryTerm();
			}
		}
		return min;
	}

}
