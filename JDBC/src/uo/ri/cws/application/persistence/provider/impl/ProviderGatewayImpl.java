package uo.ri.cws.application.persistence.provider.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class ProviderGatewayImpl implements ProviderGateway {

    @Override
    public void add(ProviderRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(ProviderRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<ProviderRecord> findById(String id) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<ProviderRecord> provider = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TPROVIDERS_FINDBYID"));
	    pst.setString(1, id);
	    rs = pst.executeQuery();

	    provider = rs.next() ? Optional.of(RecordAssembler.toProviderRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return provider;
    }

    @Override
    public List<ProviderRecord> findAll() throws SQLException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Optional<ProviderRecord> findByNIF(String nif) throws SQLException {
	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<ProviderRecord> provider = null;

	try {
	    c = Jdbc.getCurrentConnection();

	    pst = c.prepareStatement(Conf.getInstance().getProperty("TPROVIDERS_GETBYID"));
	    pst.setString(1, nif);
	    rs = pst.executeQuery();

	    provider = rs.next() ? Optional.of(RecordAssembler.toProviderRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return provider;
    }

}
