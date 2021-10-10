package uo.ri.cws.application.business.invoice.create.commands;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import alb.util.assertion.Argument;
import alb.util.math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class WorkOrdersBilling implements Command<InvoiceDto> {

    private List<String> workOrderIds;

    public WorkOrdersBilling(List<String> workOrderIds) {
	this.workOrderIds = workOrderIds;
    }

    public InvoiceDto execute() throws BusinessException, SQLException {

	validateIDS();
	InvoiceRecord invoice = new InvoiceRecord();
	InvoiceGateway iw = PersistenceFactory.forInvoice();

	checkWorkOrdersExist(workOrderIds);
	checkWorkOrdersFinished(workOrderIds);

	invoice.number = generateInvoiceNumber();
	invoice.date = LocalDate.now();
	double amount = calculateTotalInvoice(workOrderIds); // vat not included
	invoice.vat = vatPercentage(amount, invoice.date);
	double total = amount * (1 + invoice.vat / 100); // vat included
	invoice.total = Round.twoCents(total);

	invoice.id = createInvoice(invoice.number, invoice.date, invoice.vat, total);
	linkWorkordersToInvoice(invoice.id, workOrderIds);
	markWorkOrderAsInvoiced(workOrderIds);

	iw.add(invoice);

	return DtoMapper.toDto(invoice);

    }

    /*
     * checks whether every work order exist
     */
    private boolean checkWorkOrdersExist(List<String> workOrderIDS) throws BusinessException, SQLException {

	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();

	for (String id : workOrderIDS) {
	    BusinessCheck.isFalse(wg.findById(id).isEmpty(), "Workorder does not exist");
	}

	return true;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private boolean checkWorkOrdersFinished(List<String> workOrderIDS) throws SQLException, BusinessException {
	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();

	for (String id : workOrderIDS) {
	    BusinessCheck.isTrue(wg.findById(id).get().status.equals("FINISHED"), "Workorder is not finished yet");
	}

	return true;
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private Long generateInvoiceNumber() throws SQLException {

	InvoiceGateway wg = PersistenceFactory.forInvoice();
	Long number = null;

	number = wg.getNextInvoiceNumber();

	return number;
    }

    /*
     * Compute total amount of the invoice (as the total of individual work orders'
     * amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDS) throws BusinessException, SQLException {

	double totalInvoice = 0.0;
	for (String workOrderID : workOrderIDS) {
	    totalInvoice += getWorkOrderTotal(workOrderID);
	}
	return totalInvoice;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private Double getWorkOrderTotal(String workOrderID) throws SQLException, BusinessException {
	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
	Double money = 0.0;

	WorkOrderRecord workOrder = wg.findById(workOrderID).get();
	BusinessCheck.isTrue(wg.findById(workOrderID).isEmpty(), "ID does not exist");
	BusinessCheck.isTrue(workOrder.status.equals("FINISHED"), "The work order is not finish");

	return money;

    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
	return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

    }

    /*
     * Creates the invoice in the database; returns the id
     */
    private String createInvoice(long numberInvoice, LocalDate dateInvoice, double vat, double total)
	    throws SQLException {

	String idInvoice = UUID.randomUUID().toString();
	InvoiceGateway ig = PersistenceFactory.forInvoice();

	InvoiceRecord invoice = new InvoiceRecord();
	invoice.id = idInvoice;
	invoice.number = numberInvoice;
	invoice.date = dateInvoice;
	invoice.vat = vat;
	invoice.total = total;
	ig.add(invoice);

	return idInvoice;
    }

    /*
     * Set the invoice number field in work order table to the invoice number
     * generated
     */
    private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDS) throws SQLException {

	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();

	wg.linkWorkorderToInvoice(invoiceId, workOrderIDS);

    }

    /*
     * Sets status to INVOICED for every workorder
     */
    private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {

	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();

	wg.markAsInvoiced(ids);

    }

    /*
     * Valida los ids que se quieren incluir en la factura
     */
    private void validateIDS() {

	Argument.isNotNull(workOrderIds);
	Argument.isTrue(!workOrderIds.isEmpty());

	for (String id : workOrderIds) {
	    Argument.isNotEmpty(id, "ID can not be empty");

	}

    }
}