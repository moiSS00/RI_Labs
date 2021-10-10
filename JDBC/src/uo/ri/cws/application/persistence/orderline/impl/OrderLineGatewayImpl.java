package uo.ri.cws.application.persistence.orderline.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.orderline.OrderLineGateway;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class OrderLineGatewayImpl implements OrderLineGateway {

    @Override
    public void add(OrderLineRecord orderline) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERLINES_ADD"));

	    pst.setDouble(1, orderline.price);
	    pst.setInt(2, orderline.quantity);
	    pst.setString(3, orderline.sparePart_id);
	    pst.setString(4, orderline.order_id);

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
    public void update(OrderLineRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<OrderLineRecord> findById(String id) throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<OrderLineRecord> findAll() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<OrderLineRecord> findByOrderId(String orderId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<OrderLineRecord> orders = new ArrayList<OrderLineRecord>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TORDERS_FINDBYORDERID"));
	    pst.setString(1, orderId);
	    rs = pst.executeQuery();
	    orders = RecordAssembler.toOrderLineList(rs);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return orders;
    }

}
