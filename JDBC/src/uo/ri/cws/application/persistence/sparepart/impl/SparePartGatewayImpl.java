package uo.ri.cws.application.persistence.sparepart.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class SparePartGatewayImpl implements SparePartGateway {

    @Override
    public void add(SparePartRecord sparePart) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_ADD"));

	    pst.setString(1, sparePart.id);
	    pst.setLong(2, 1);
	    pst.setString(3, sparePart.code);
	    pst.setString(4, sparePart.description);
	    pst.setInt(5, sparePart.stock);
	    pst.setInt(6, sparePart.minStock);
	    pst.setInt(7, sparePart.maxStock);
	    pst.setDouble(8, sparePart.price);

	    pst.executeUpdate();

	} finally {
	    Jdbc.close(pst);
	}

    }

    @Override
    public void remove(String code) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_REMOVE"));
	    pst.setString(1, code);
	    pst.executeUpdate();

	} finally {
	    Jdbc.close(pst);
	}

    }

    @Override
    public void update(SparePartRecord sparePart) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_UPDATE"));
	    pst.setString(1, sparePart.description);
	    pst.setInt(2, sparePart.stock);
	    pst.setInt(3, sparePart.minStock);
	    pst.setInt(4, sparePart.maxStock);
	    pst.setDouble(5, sparePart.price);
	    pst.setString(6, sparePart.id);

	    pst.executeUpdate();

	} finally {
	    Jdbc.close(pst);
	}

    }

    @Override
    public Optional<SparePartRecord> findById(String id) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<SparePartRecord> sparePart = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDBYID"));
	    pst.setString(1, id);
	    rs = pst.executeQuery();

	    sparePart = rs.next() ? Optional.of(RecordAssembler.toSparePartRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return sparePart;
    }

    @Override
    public List<SparePartRecord> findAll() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Optional<SparePartRecord> findByCode(String code) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<SparePartRecord> sparePart = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDBYCODE"));
	    pst.setString(1, code);
	    rs = pst.executeQuery();

	    sparePart = rs.next() ? Optional.of(RecordAssembler.toSparePartRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return sparePart;
    }

    @Override
    public List<SparePartRecord> listUnderStock() throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<SparePartRecord> spareParts = new ArrayList<SparePartRecord>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_LISTUNDERSTOCK"));
	    rs = pst.executeQuery();

	    spareParts = RecordAssembler.toSparePartList(rs);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return spareParts;

    }

    @Override
    public List<String> getUsedSparePartsIds() throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<SparePartRecord> spareParts = new ArrayList<SparePartRecord>();
	List<String> ids = new ArrayList<String>();

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_GETUSEDSPAREPARTS"));
	    rs = pst.executeQuery();
	    spareParts = RecordAssembler.toSparePartList(rs);
	    for (SparePartRecord sp : spareParts) {
		ids.add(sp.id);
	    }

	} finally {
	    Jdbc.close(pst);
	}

	return ids;

    }

    @Override
    public List<SparePartRecord> findByDescription(String description) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<SparePartRecord> sparePart = new ArrayList<SparePartRecord>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_FINDBYDESCRIPTION"));
	    pst.setString(1, description);
	    rs = pst.executeQuery();

	    sparePart = RecordAssembler.toSparePartList(rs);

	} finally {
	    Jdbc.close(rs, pst);
	}

	return sparePart;
    }

    @Override
    public List<String> getSparePartsInSubstitutions() throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<String> ids = new ArrayList<String>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_GETSPAREPARTSINSUBSITUTIONS"));
	    rs = pst.executeQuery();

	    while (rs.next()) {
		ids.add(rs.getString("code"));
	    }

	} finally {
	    Jdbc.close(rs, pst);
	}

	return ids;
    }

    @Override
    public List<String> getNotOrderedSpareParts() throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	List<String> ids = new ArrayList<String>();

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TSPAREPARTS_GETNOORDEREDSPAREPARTS"));
	    rs = pst.executeQuery();

	    while (rs.next()) {
		ids.add(rs.getString(1));
	    }

	} finally {
	    Jdbc.close(rs, pst);
	}

	return ids;
    }

}
