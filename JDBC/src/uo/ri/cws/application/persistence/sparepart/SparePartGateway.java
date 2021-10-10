package uo.ri.cws.application.persistence.sparepart;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface SparePartGateway extends Gateway<SparePartRecord> {

    /**
     * Devuelve un repuesto (identificado por un codigo)
     * 
     * @param code Codigo del repuesto deseado
     * @return El repuesto encontrado
     * @throws SQLException
     */
    Optional<SparePartRecord> findByCode(String code) throws SQLException;

    /**
     * Devuelve una lista con los respuestos cuyo esto actual esta por debajo del
     * minStock
     * 
     * @return Lista de repuestos que estan por debajo del limite inferior de stock
     * @throws SQLException
     */
    List<SparePartRecord> listUnderStock() throws SQLException;

    /**
     * Devuelve una lista con los repuestos que fueron usados en algun pedido al
     * menos una vez
     * 
     * @return Lista de repuestos que fueron usados en pedidos
     * @throws SQLException
     */
    List<String> getUsedSparePartsIds() throws SQLException;

    /**
     * Devuelve una lista con los repuestos cuya descripcion coincide parcial o
     * totalmente con la cadena dada
     * 
     * @param description Cadena a buscar
     * @return Lista de repuestos cuya descripcion contiene la cadena dada
     * @throws SQLException
     */
    List<SparePartRecord> findByDescription(String description) throws SQLException;

    /**
     * Devuelve una lista con los ids de los repuestos que estan actualmente en una
     * substituion
     * 
     * @return Lista de ids de repuestos
     * @throws SQLException
     */
    List<String> getSparePartsInSubstitutions() throws SQLException;

    /**
     * Devuelve una lista con los ids de los repuestos cuyo stock esta por debajo
     * del minimo y que no se han pedido
     * 
     * @return Lista de ids de repuestos
     * @throws SQLException
     */
    List<String> getNotOrderedSpareParts() throws SQLException;

}
