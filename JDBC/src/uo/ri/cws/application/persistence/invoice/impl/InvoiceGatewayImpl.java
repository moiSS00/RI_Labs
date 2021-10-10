package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class InvoiceGatewayImpl implements InvoiceGateway {

    @Override
    public void add(InvoiceRecord invoice) throws SQLException {

	Connection c = null;
	PreparedStatement pst = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TINVOICES_ADD"));
	    pst.setString(1, invoice.id);
	    pst.setLong(2, invoice.number);
	    pst.setDate(3, java.sql.Date.valueOf(invoice.date));
	    pst.setDouble(4, invoice.vat);
	    pst.setDouble(5, invoice.total);
	    pst.setString(6, "NOT_YET_PAID");
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
    public void update(InvoiceRecord t) throws SQLException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<InvoiceRecord> findById(String id) throws SQLException {

	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	Optional<InvoiceRecord> invoice = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TINVOICES_FINDBYID"));
	    rs = pst.executeQuery();

	    invoice = rs.next() ? Optional.of(RecordAssembler.toInvoiceRecord(rs)) : Optional.empty();

	} finally {
	    Jdbc.close(rs, pst);
	}

	return invoice;
    }

    @Override
    public List<InvoiceRecord> findAll() throws SQLException {
	return null;
    }

    @Override
    public Optional<InvoiceRecord> findByNumber(Long number) {
	return null;
    }

    @Override
    public Long getNextInvoiceNumber() throws SQLException {

	Connection c = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    c = Jdbc.getCurrentConnection();
	    pst = c.prepareStatement(Conf.getInstance().getProperty("TINVOICES_NUMBER"));
	    rs = pst.executeQuery();
	    if (rs.next()) {
		return rs.getLong(1) + 1; // +1, next
	    } else { // there is none yet
		return 1L;
	    }

	} finally {
	    Jdbc.close(rs, pst);
	}

    }

}
