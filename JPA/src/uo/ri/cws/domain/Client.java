package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Client extends BaseEntity {

	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	private Set<Vehicle> vehicles = new HashSet<Vehicle>();

	private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();

	Client() {
	}

	public Client(String dni) {
		this(dni, "client-name", "client-surname", "client-email", "client-phone", new Address());
	}

	public Client(String dni, String name, String surname) {
		this(dni, name, surname, "client-email", "client-phone", new Address());
	}

	public Client(String dni, String name, String surname, String email, String phone, Address address) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		ArgumentChecks.isNotEmpty(email);
		ArgumentChecks.isNotEmpty(phone);
		ArgumentChecks.isNotNull(address);
		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<PaymentMean>(paymentMeans);
	}

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<Vehicle>(vehicles);
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Client other = (Client) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
				+ phone + ", address=" + address + "]";
	}

}
