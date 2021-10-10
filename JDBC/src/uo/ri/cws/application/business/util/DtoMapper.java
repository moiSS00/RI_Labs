package uo.ri.cws.application.business.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.order.OrderDto;
import uo.ri.cws.application.business.order.OrderDto.OrderLineDto;
import uo.ri.cws.application.business.provider.ProviderDto;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.order.OrderRecord;
import uo.ri.cws.application.persistence.orderline.OrderLineRecord;
import uo.ri.cws.application.persistence.provider.ProviderRecord;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;

public class DtoMapper {

	public static MechanicDto toDto(String id, String dni, String name, String surname) {
		MechanicDto result = new MechanicDto();
		result.id = id;
		result.name = name;
		result.surname = surname;
		result.dni = dni;
		return result;
	}
	 

	public static Optional<MechanicDto> toDto(Optional<MechanicRecord> arg) {
		Optional<MechanicDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toDto(arg.get().id, arg.get().dni, arg.get().name, arg.get().surname));
		return result;
	}
	

	public static List<MechanicDto> toDtoList(List<MechanicRecord> arg) {
		List<MechanicDto> result = new ArrayList<MechanicDto> ();
		for (MechanicRecord mr : arg) 
			result.add(toDto(mr.id, mr.dni, mr.name, mr.surname));
		return result;
	}

	public static MechanicRecord toRecord(MechanicDto arg) {
		MechanicRecord result = new MechanicRecord ();
		result.id = arg.id;
		result.dni = arg.dni;
		result.name = arg.name;
		result.surname = arg.surname;
		return result;
	}

	public static InvoiceDto toDto(InvoiceRecord arg) {
		InvoiceDto result = new InvoiceDto();
		result.id = arg.id;
		result.number = arg.number;
		result.status = arg.status;
		result.date = arg.date;
		result.total = arg.total;
		result.vat = arg.vat;
		return result;
	}
	
	public static List<SparePartDto> toDtoListSparePart(List<SparePartRecord> arg) {
		List<SparePartDto> result = new ArrayList<SparePartDto> ();
		for (SparePartRecord mr : arg) 
			result.add(toDto(mr));
		return result;
	}
	
	public static SparePartRecord toRecord(SparePartDto arg) {
		SparePartRecord result = new SparePartRecord();
		result.id = arg.id;
		result.version = arg.version; 
		result.code = arg.code;
		result.description = arg.description; 
		result.stock = arg.stock; 
		result.minStock = arg.minStock; 
		result.maxStock = arg.maxStock; 
		result.price = arg.price; 
		return result;
	}
	
	public static SparePartDto toDto(SparePartRecord arg) {
		SparePartDto result = new SparePartDto();
		result.id = arg.id;
		result.version = arg.version; 
		result.code = arg.code;
		result.description = arg.description; 
		result.stock = arg.stock; 
		result.minStock = arg.minStock; 
		result.maxStock = arg.maxStock; 
		result.price = arg.price; 
		return result;
	}

	public static ProviderDto toDto(ProviderRecord arg) {
		ProviderDto result = new ProviderDto();
		result.id = arg.id;
		result.version = arg.version; 
		result.nif = arg.nif;
		result.name = arg.name; 
		result.email = arg.email; 
		result.phone = arg.phone;  

		return result;
	}

	public static OrderLineRecord toRecord(OrderLineDto line, String orderId) {
		OrderLineRecord result = new OrderLineRecord();
		result.price = line.price; 
		result.quantity = line.quantity;
		result.sparePart_id = line.sparePart.id; 
		result.order_id = orderId; 


		return result;
	}

	
	public static OrderRecord toRecord(OrderDto order) {
		OrderRecord result = new OrderRecord(); 
		result.id = order.id; 
		result.version = order.version; 
		result.code = order.code; 
		result.orderedDate = order.orderedDate; 
		result.receptionDate = order.receptionDate; 
		result.amount = order.amount; 
		result.status = order.status; 
		result.providerId = order.provider.id; 
		return result; 
	}
	
	public static OrderDto toDto(OrderRecord order) {
		OrderDto result = new OrderDto(); 
		result.id = order.id; 
		result.version = order.version; 
		result.code = order.code; 
		result.orderedDate = order.orderedDate; 
		result.receptionDate = order.receptionDate; 
		result.amount = order.amount; 
		result.status = order.status; 
		result.provider.id = order.providerId; 
		return result; 
	}

	public static List<OrderDto> toDtoListOrders(List<OrderRecord> arg) {
		List<OrderDto> result = new ArrayList<OrderDto> ();
		for (OrderRecord or : arg) 
			result.add(toDto(or));
		return result;
	}
	
	/*
	 * Recibe como parametro la sparepart y el numero total de ventas de esa sparepart 
	 */
	public static SparePartReportDto toSparePartReportDto(SparePartRecord sparePart, int totalUnits) {
		SparePartReportDto s = new SparePartReportDto(); 
		s.id = sparePart.id; 
		s.version = sparePart.version; 
		s.code = sparePart.code; 
		s.description = sparePart.description; 
		s.stock = sparePart.stock; 
		s.minStock = sparePart.minStock; 
		s.maxStock = sparePart.maxStock;
		s.price = sparePart.price;
		s.totalUnitsSold = totalUnits;
		return s; 
	}
	

	

	
	
}
