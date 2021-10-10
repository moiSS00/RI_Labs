package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Provider;

public interface ProviderRepository extends Repository<Provider> {

	/**
	 * Devuelve un proveedor con un determinado nif
	 * 
	 * @param nif Nif a buscar
	 * @return Proveedor con el nif pasado como parametro
	 */
	Optional<Provider> findByNif(String nif);

	/**
	 * Devuelve una lista de proveedores con un nombre, un email y un numero de
	 * telefono específicos a la vez
	 * 
	 * @param name  Nombre a buscar
	 * @param email Email a buscar
	 * @param phone Telefono a buscar
	 * @return Lista de proveedores con ese nombre, apellido y telefono a la vez
	 */
	List<Provider> findByNameMailPhone(String name, String email, String phone);

	List<Provider> findByName(String name);

	List<Provider> findBySparePartCode(String code);

}
