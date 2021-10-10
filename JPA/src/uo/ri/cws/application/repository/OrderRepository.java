package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Order;

public interface OrderRepository extends Repository<Order> {

	/**
	 * Busca un pedido por codigo
	 * 
	 * @param code Codigo a buscar
	 * @return Devuelve el pedido con el codigo en especifico
	 */
	Optional<Order> findByCode(String code);

	/**
	 * Busca los pedido de un proveedor en concreto por el nif
	 * 
	 * @param nif Nif del proveedor a buscar
	 * @return Una lista con los pedidos del proveedor cuyo nif es el que se paso
	 *         como parametro
	 */
	List<Order> findByProviderNif(String nif);

	/**
	 * Busca los pedidos en los que en alguna de sus orderLines este un repuesto en
	 * especifico identificado por su codigo
	 * 
	 * @param code Codigo del repuesto a buscar
	 * @return Lista con los pedidos que en sus orderLines tienen el repuesto cuyo
	 *         codigo correspone al pasado como paramtro
	 */
	List<Order> findBySparePartCode(String code);

}
