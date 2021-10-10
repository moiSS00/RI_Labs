package uo.ri.cws.application.persistence.supply.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.supply.SupplyGateway;
import uo.ri.cws.application.persistence.supply.SupplyRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class SupplyGatewayImpl implements SupplyGateway {

    @Override
    public void add(SupplyRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(SupplyRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<SupplyRecord> findById(String id) throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<SupplyRecord> findAll() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<String> getMinimunPriceSupply(String sparePartId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<String> ids = new ArrayList<String>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSUPPLIES_GETMINIMUNPRICESUPPLY"));
	    pst.setString(1, sparePartId);
	    rs = pst.executeQuery();

	    while (rs.next()) {
		ids.add(rs.getString(1));
	    }

	} finally {
	    Jdbc.close(rs, pst);
	}

	return ids;
    }

    @Override
    public List<String> getMinimunDeliverSupply(String sparePartId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<String> ids = new ArrayList<String>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSUPPLIES_GETMINIMUNDELIVERSUPPLY"));
	    pst.setString(1, sparePartId);
	    pst.setString(2, sparePartId);
	    rs = pst.executeQuery();

	    while (rs.next()) {
		ids.add(rs.getString(1));
	    }

	} finally {
	    Jdbc.close(rs, pst);
	}

	return ids;
    }

    @Override
    public String getFirstSupplyInOrder(String sparePartId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	String id = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSUPPLIES_GETFIRSTSUPPLYINORDER"));
	    pst.setString(1, sparePartId);
	    pst.setString(2, sparePartId);
	    rs = pst.executeQuery();
	    rs.next();
	    id = rs.getString(1);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return id;
    }

    @Override
    public Double getPriceByProviderAndSpare(String idProvider, String idSparePart) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Double price = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSUPPLIES_GETPRICEBYPROVIDERANDSPARE"));
	    pst.setString(1, idProvider);
	    pst.setString(2, idSparePart);
	    rs = pst.executeQuery();
	    rs.next();
	    price = rs.getDouble(1);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return price;
    }

    @Override
    public List<SupplyRecord> findBySparePartId(String sparePartId) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<SupplyRecord> supplies = new ArrayList<SupplyRecord>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSUPPLIES_FINDBYSPAREPARTID"));
	    pst.setString(1, sparePartId);
	    rs = pst.executeQuery();

	    supplies = RecordAssembler.toSupplyRecordList(rs);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return supplies;
    }

}
