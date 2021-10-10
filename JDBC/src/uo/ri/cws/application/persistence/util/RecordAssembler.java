package uo.ri.cws.application.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.supply.SupplyRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class RecordAssembler {

    public static MechanicRecord toMechanicRecord(ResultSet m) throws SQLException {
	MechanicRecord dto = new MechanicRecord();
	dto.id = m.getString("id");

	dto.dni = m.getString("dni");
	dto.name = m.getString("name");
	dto.surname = m.getString("surname");
	return dto;
    }

    public static SparePartRecord toSparePartRecord(ResultSet m) throws SQLException {
	SparePartRecord dto = new SparePartRecord();
	dto.id = m.getString("id");
	dto.version = m.getLong("version");
	dto.code = m.getString("code");
	dto.description = m.getString("description");
	dto.stock = m.getInt("stock");
	dto.minStock = m.getInt("minStock");
	dto.maxStock = m.getInt("maxStock");
	dto.price = m.getDouble("price");
	return dto;
    }

    public static List<SparePartRecord> toSparePartList(ResultSet rs) throws SQLException {
	List<SparePartRecord> res = new ArrayList<>();
	while (rs.next()) {
	    res.add(toSparePartRecord(rs));
	}

	return res;
    }

    public static List<MechanicRecord> toMechanicRecordList(ResultSet rs) throws SQLException {
	List<MechanicRecord> res = new ArrayList<>();
	while (rs.next()) {
	    res.add(toMechanicRecord(rs));
	}

	return res;
    }

    public static InvoiceRecord toInvoiceRecord(ResultSet rs) throws SQLException {
	InvoiceRecord result = new InvoiceRecord();

	result.id = rs.getString("id");
	result.version = rs.getLong("version");
	result.date = rs.getDate("date").toLocalDate();
	result.number = rs.getLong("number");
	result.status = rs.getString("status");
	result.vat = rs.getDouble("vat");

	return result;
    }

    public static WorkOrderRecord toWorkOrderRecord(ResultSet rs) throws SQLException {
	WorkOrderRecord result = new WorkOrderRecord();

	result.id = rs.getString("id");
	result.vehicleId = rs.getString("vehicle_Id");
	result.description = rs.getString("description");
	result.date = rs.getDate("date");
	result.total = rs.getLong("amount");
	result.status = rs.getString("status");

	// might be null
	result.mechanicId = rs.getString("mechanic_Id");
	result.invoiceId = rs.getString("invoice_Id");

	return result;

    }

    public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs) throws SQLException {
	List<WorkOrderRecord> res = new ArrayList<>();
	while (rs.next()) {
	    res.add(toWorkOrderRecord(rs));
	}
	return res;
    }

    public static ProviderRecord toProviderRecord(ResultSet rs) throws SQLException {
	ProviderRecord result = new ProviderRecord();

	result.id = rs.getString("id");
	result.version = rs.getLong("version");
	result.nif = rs.getString("nif");
	result.name = rs.getString("name");
	result.email = rs.getString("email");
	result.phone = rs.getString("phone");

	return result;
    }

    public static OrderRecord toOrderRecord(ResultSet rs) throws SQLException {
	OrderRecord result = new OrderRecord();

	result.id = rs.getString("id");
	result.code = rs.getString("code");
	result.version = rs.getLong("version");
	result.amount = rs.getDouble("amount");
	result.status = rs.getString("status");
	result.orderedDate = rs.getDate("ordereddate").toLocalDate();
	if (rs.getDate("receptionDate") != null) {
	    result.receptionDate = rs.getDate("receptionDate").toLocalDate();
	}
	result.providerId = rs.getString("provider_id");

	return result;
    }

    public static List<OrderRecord> toOrderList(ResultSet rs) throws SQLException {
	List<OrderRecord> result = new ArrayList<OrderRecord>();
	while (rs.next()) {
	    result.add(toOrderRecord(rs));
	}
	return result;
    }

    public static List<OrderLineRecord> toOrderLineList(ResultSet rs) throws SQLException {
	List<OrderLineRecord> result = new ArrayList<OrderLineRecord>();
	while (rs.next()) {
	    result.add(toOrderLineRecord(rs));
	}
	return result;
    }

    public static OrderLineRecord toOrderLineRecord(ResultSet rs) throws SQLException {
	OrderLineRecord result = new OrderLineRecord();

	result.order_id = rs.getString("order_id");
	result.price = rs.getDouble("price");
	result.quantity = rs.getInt("quantity");
	result.sparePart_id = rs.getString("sparepart_id");

	return result;
    }

    public static List<SupplyRecord> toSupplyRecordList(ResultSet rs) throws SQLException {
	List<SupplyRecord> result = new ArrayList<SupplyRecord>();
	while (rs.next()) {
	    result.add(toSupplyRecord(rs));
	}
	return result;
    }

    public static SupplyRecord toSupplyRecord(ResultSet rs) throws SQLException {
	SupplyRecord result = new SupplyRecord();

	result.id = rs.getString("id");
	result.version = rs.getLong("version");
	result.providerId = rs.getString("provider_id");
	result.sparePartId = rs.getString("sparepart_id");
	result.price = rs.getInt("price");
	result.deliveryTerm = rs.getInt("deliveryterm");

	return result;
    }

//	public static InterventionRecord toInterventionRecord(ResultSet rs) throws SQLException {
//		InterventionRecord result = new InterventionRecord();
//		result.id = rs.getString("id");
//		result.mechanicId = rs.getString("mechanic_id");
//		result.workorderId = rs.getString("workorder_id");
//		result.date = rs.getDate("date");
//		result.minutes = rs.getInt("minutes");
//		return result;
//		
//	}

}
