package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkOrderRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(WorkOrderRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<WorkOrderRecord> findById(String id) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<WorkOrderRecord> workOrder = null;

	try {

	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINDBYID"));
	    pst.setString(1, id);
	    rs = pst.executeQuery();

	    workOrder = rs.next() ? Optional.of(RecordAssembler.toWorkOrderRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return workOrder;
    }

    @Override
    public List<WorkOrderRecord> findAll() throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<WorkOrderRecord> workOrders = new ArrayList<WorkOrderRecord>();

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINDALL"));
	    rs = pst.executeQuery();
	    workOrders = RecordAssembler.toWorkOrderRecordList(rs);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return workOrders;
    }

    @Override
    public List<WorkOrderRecord> findByIds(List<String> workOrderIds) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<WorkOrderRecord> workOrders = new ArrayList<WorkOrderRecord>();

	try {

	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINDBYID"));

	    for (String id : workOrderIds) {
		pst.setString(1, id);
		rs = pst.executeQuery();
		workOrders.add(RecordAssembler.toWorkOrderRecord(rs));
	    }

	} finally {
	    Jdbc.close(rs, pst);
	}

	return workOrders;
    }

    @Override
    public List<WorkOrderRecord> findByVehicleId(String id) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<WorkOrderRecord> workOrders = new ArrayList<WorkOrderRecord>();

	try {

	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINDBYVEHICLEID"));
	    rs = pst.executeQuery();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return workOrders;
    }

    @Override
    public List<WorkOrderRecord> findByMechanicId(String id) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<WorkOrderRecord> workOrders = new ArrayList<WorkOrderRecord>();

	try {

	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINDBYMECHANICID"));
	    rs = pst.executeQuery();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return workOrders;
    }

    @Override
    public List<WorkOrderRecord> findByStatus(String status) {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<WorkOrderRecord> workOrders = new ArrayList<WorkOrderRecord>();

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FINDBYSTATUS"));
	    pst.setString(1, status);
	    rs = pst.executeQuery();
	    return RecordAssembler.toWorkOrderRecordList(rs);

	} catch (SQLException e) {
	    return workOrders;
	} finally {
	    Jdbc.close(rs, pst);
	}

    }

    @Override
    public void linkWorkorderToInvoice(String invoiceId, List<String> workOrderIDS) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_LINK_WORKORDER_TO_INVOICE"));

	    for (String breakdownId : workOrderIDS) {
		pst.setString(1, invoiceId);
		pst.setString(2, breakdownId);

		pst.executeUpdate();
	    }

	} finally {
	    Jdbc.close(pst);
	}

    }

    @Override
    public void markAsInvoiced(List<String> ids) throws SQLException {

	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_MARK_WORKORDER_AS_INVOICED"));

	    for (String id : ids) {
		pst.setString(1, id);

		pst.executeUpdate();
	    }

	} finally {
	    Jdbc.close(pst);
	}

    }

    @Override
    public int getUdsOfSparePart(String sparePartId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_GETUDSOFSPAREPART"));
	    pst.setString(1, sparePartId);
	    rs = pst.executeQuery();
	    rs.next();
	    return rs.getInt("uds");

	} finally {
	    Jdbc.close(rs, pst);
	}
    }

}
