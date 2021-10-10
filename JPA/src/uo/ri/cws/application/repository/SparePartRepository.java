package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.SparePart;

public interface SparePartRepository extends Repository<SparePart> {

	/**
	 * Devuelve un repuesto con un determinado codigo
	 * 
	 * @param code Codigo a buscar
	 * @return El repuesto con ese codigo
	 */
	Optional<SparePart> findByCode(String code);

	/**
	 * Devuelve una lista de los repuestos cuyo stock es menor que el stock minimo
	 * permitido y no esten en ningun pedido en estado PENDING
	 * 
	 * @return Lista de repuestos con los criterios ya comentados
	 */
	List<SparePart> findUnderStockNotPending();

	/**
	 * Busca los repuestos cuya descripcion coincide total o parcialmente con la
	 * descripcion pasada como parametro
	 * 
	 * @param description Cadena a buscar
	 * @return Lista con los repuestos cuya descripcion coincide total o
	 *         parcialmente con la descripcion pasada como paramtro
	 */
	List<SparePart> findByDescription(String description);

}
