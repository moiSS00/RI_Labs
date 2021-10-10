package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Supply;

public interface SupplyRepository extends Repository<Supply> {

	/**
	 * Busca un suministro por nif de proveedor y por codigo de repuesto
	 * 
	 * @param nif  Nif del proveedor
	 * @param code Codigo del repuesto
	 * @return El suministro cuyo proveedor tenga el nif especificado y el que
	 *         suministre el repuesto que tenga por codigo el especificado
	 */
	Optional<Supply> findByNifAndCode(String nif, String code);

	/**
	 * Devuelve una lista de los suministros de un proveedor con un determinado nif
	 * 
	 * @param nif Nif del proveedor
	 * @return Lista de los suministros del proveedor especificado por nif
	 */
	List<Supply> findByProviderNif(String nif);

	/**
	 * Devuelve una lista de suministros de un repuesto con un determinado codigo
	 * 
	 * @param code Codigo del repuesto
	 * @return Lista de los suministros del repuesto especificado por codigo
	 */
	List<Supply> findBySparePartCode(String code);
	


}
