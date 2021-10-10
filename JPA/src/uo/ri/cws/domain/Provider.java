package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Provider extends BaseEntity {

	private String nif;
	private String name;
	private String email;
	private String phone;

	private Set<Supply> supplies = new HashSet<Supply>();

	private Set<Order> orders = new HashSet<Order>();

	public Provider() {

	}

	public Provider(String nif) {
		this(nif, "provider-name", "provider-email", "provider-phone");
	}

	public Provider(String nif, String name, String email, String phone) {
		ArgumentChecks.isNotEmpty(nif);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(email);
		ArgumentChecks.isNotEmpty(phone);
		this.nif = nif;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public String getNif() {
		return nif;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Set<Supply> getSupplies() {
		return new HashSet<Supply>(supplies);
	}

	public Set<Order> getOrders() {
		return new HashSet<Order>(orders);
	}

	Set<Supply> _getSupplies() {
		return supplies;
	}

	Set<Order> _getOrders() {
		return orders;
	}

	public void setName(String name) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}

	public void setEmail(String email) {
		ArgumentChecks.isNotEmpty(email);
		this.email = email;
	}

	public void setPhone(String phone) {
		ArgumentChecks.isNotEmpty(phone);
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
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
		Provider other = (Provider) obj;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Provider [nif=" + nif + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
	}

}
