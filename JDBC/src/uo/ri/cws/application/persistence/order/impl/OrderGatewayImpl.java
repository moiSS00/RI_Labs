package uo.ri.cws.application.persistence.order.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.order.OrderGateway;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class OrderGatewayImpl implements OrderGateway {

    @Override
    public void add(OrderRecord order) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_ADD"));

	    pst.setString(1, order.id);
	    pst.setDouble(2, order.amount);
	    pst.setString(3, order.code);
	    pst.setDate(4, java.sql.Date.valueOf(order.orderedDate));
	    pst.setString(5, order.status);
	    pst.setLong(6, order.version);
	    pst.setString(7, order.providerId);

	    pst.executeUpdate();

	} finally {
	    Jdbc.close(pst);
	}

    }

    @Override
    public void remove(String id) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(OrderRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<OrderRecord> findById(String id) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<OrderRecord> order = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYID"));
	    pst.setString(1, id);
	    rs = pst.executeQuery();

	    order = rs.next() ? Optional.of(RecordAssembler.toOrderRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return order;

    }

    @Override
    public List<OrderRecord> findAll() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<OrderRecord> findByProviderId(String providerId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<OrderRecord> orders = new ArrayList<OrderRecord>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYPROVIDERID"));
	    pst.setString(1, providerId);
	    rs = pst.executeQuery();
	    orders = RecordAssembler.toOrderList(rs);
	} finally {
	    Jdbc.close(rs, pst);
	}

	return orders;
    }

    @Override
    public Optional<OrderRecord> findByCode(String code) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<OrderRecord> order = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYCODE"));
	    pst.setString(1, code);
	    rs = pst.executeQuery();

	    order = rs.next() ? Optional.of(RecordAssembler.toOrderRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return order;
    }

}
