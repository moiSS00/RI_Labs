package uo.ri.cws.application.business.sparepart.report.commands;

import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class ViewDetail implements Command<Optional<SparePartReportDto>> {

    private String code;

    public ViewDetail(String code) {
	this.code = code;
    }

    public Optional<SparePartReportDto> execute() throws SQLException {
	SparePartGateway sg = PersistenceFactory.forSparePart();
	Optional<SparePartRecord> sparePart = sg.findByCode(code);

	Optional<SparePartReportDto> result = sparePart.isEmpty() ? Optional.ofNullable(null)
		: Optional.ofNullable(createSparePartRecordDto(sparePart.get()));

	return result;
    }

    private SparePartReportDto createSparePartRecordDto(SparePartRecord sparePart) throws SQLException {

	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();

	SparePartReportDto s = new SparePartReportDto();
	s.id = sparePart.id;
	s.version = sparePart.version;
	s.code = sparePart.code;
	s.description = sparePart.description;
	s.stock = sparePart.stock;
	s.minStock = sparePart.minStock;
	s.maxStock = sparePart.maxStock;
	s.price = sparePart.price;
	s.totalUnitsSold = wg.getUdsOfSparePart(sparePart.id);

	return s;
    }

}
