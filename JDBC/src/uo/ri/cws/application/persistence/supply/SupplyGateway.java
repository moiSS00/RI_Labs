package uo.ri.cws.application.persistence.supply;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface SupplyGateway extends Gateway<SupplyRecord> {

    /**
     * Nos devuelve una lista con los ids de los proveedores que suministran un
     * repuesto en especifico (identificado por su id) al menor precio posible
     * 
     * @param sparePartId Id del repuesto deseado
     * @return Lista de ids de proveedores
     * @throws SQLException
     */
    public List<String> getMinimunPriceSupply(String sparePartId) throws SQLException;

    /**
     * Nos devuelve una lista con los ids de los proveedores que suministran un
     * repuesto en especifico (identificado por su id) al menor precio posible y en
     * el menor plazo de entrega posible
     * 
     * @param sparePartId Id del repuesto deseado
     * @return Lista de ids de proveedores
     * @throws SQLException
     */
    public List<String> getMinimunDeliverSupply(String sparePartId) throws SQLException;

    /**
     * Escoge el primer id por orden lexicografico de los proveedores que
     * suministran un repuesto en especifico (identificado por su id) al menor
     * precio posible y en el menor plazo de entrega posible
     * 
     * @param sparePartId Id del repuesto deseado
     * @return EL id del proveedor obtnido
     * @throws SQLException
     */
    public String getFirstSupplyInOrder(String sparePartId) throws SQLException;

    /**
     * Devuelve el precio al que un determinado proveedor (identificado por su id)
     * suministra una determinado repuesto (identificado tambien por su id)
     * 
     * @param idProvider  Id del proveedor deseado
     * @param idSparePart Id del repuesto deseado
     * @return El precio del repuesto indicado a traves de un determinado proveedor
     * @throws SQLException
     */
    public Double getPriceByProviderAndSpare(String idProvider, String idSparePart) throws SQLException;

    List<SupplyRecord> findBySparePartId(String sparePartId) throws SQLException;
}
