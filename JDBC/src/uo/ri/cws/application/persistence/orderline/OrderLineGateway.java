package uo.ri.cws.application.persistence.orderline;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface OrderLineGateway extends Gateway<OrderLineRecord> {

    /**
     * Devuelve las order lines de un determinado pedido (identificado por su id)
     * 
     * @param orderId Id del pedido deseado
     * @return Lista de order lines del pedido indicado
     * @throws SQLException
     */
    List<OrderLineRecord> findByOrderId(String orderId) throws SQLException;
}
