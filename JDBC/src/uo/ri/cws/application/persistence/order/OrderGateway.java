package uo.ri.cws.application.persistence.order;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface OrderGateway extends Gateway<OrderRecord> {

    /**
     * Devuelve una lista de los pedidos de un determindao proveedor (identificado
     * por su id)
     * 
     * @param providerId Id del proveedor deseado
     * @return Lista de pedidos bajo un proveedor
     * @throws SQLException
     */
    List<OrderRecord> findByProviderId(String providerId) throws SQLException;

    /**
     * Devuelve un pedido con un determinado codigo
     * 
     * @param code Codigo del pedido a obtener
     * @return Pedido con el codigo indicado
     * @throws SQLException
     */
    Optional<OrderRecord> findByCode(String code) throws SQLException;

}
