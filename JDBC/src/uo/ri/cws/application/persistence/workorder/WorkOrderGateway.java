package uo.ri.cws.application.persistence.workorder;

import java.sql.SQLException;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    /**
     * @param workorder Ids, list of workorders to retrieve
     * @return list of workorder dto whose id is included in the parameter, probably
     *         empty
     * @throws SQLException
     */
    List<WorkOrderRecord> findByIds(List<String> workOrderIds) throws SQLException;

    /**
     * @return a list of all work orders for an specific vehicle id
     * @throws SQLException
     */
    List<WorkOrderRecord> findByVehicleId(String id) throws SQLException;

    /**
     * @param a mechanic id
     * @return a list of all work orders assigned to this specific mechanic
     * @throws SQLException
     */
    List<WorkOrderRecord> findByMechanicId(String id) throws SQLException;

    /**
     * @param the state
     * @return a list of all work orders in the specific state
     * 
     */
    List<WorkOrderRecord> findByStatus(String status);

    /**
     * 
     * @param invoiceId    Un id de invoice
     * @param workOrderIDS Lista de ids de workOrders
     * @throws SQLException
     */
    void linkWorkorderToInvoice(String invoiceId, List<String> workOrderIDS) throws SQLException;

    /**
     * 
     * @param ids Lista de ids de workOrders
     * @throws SQLException
     */
    void markAsInvoiced(List<String> ids) throws SQLException;

    /**
     * Devuelve el numero de ventas de un determinado repuesto (se asume que se
     * cuentan como ventas aquellas unidades que se utilizaron en una workorder que
     * pertenece a una factura pagada)
     * 
     * @param sparePartId Id del repuesto deseado
     * @return Numero de unidades vendidas de ese repuesto
     * @throws SQLException
     */
    int getUdsOfSparePart(String sparePartId) throws SQLException;
}