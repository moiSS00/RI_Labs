package uo.ri.cws.application.business.sparepart.report.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class ListUnderStock implements Command<List<SparePartReportDto>> {

    public List<SparePartReportDto> execute() throws SQLException {

	SparePartGateway spg = PersistenceFactory.forSparePart();
	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
	List<SparePartRecord> spareParts = spg.listUnderStock();
	List<SparePartReportDto> sparePartsReport = new ArrayList<SparePartReportDto>();
	for (SparePartRecord sparePart : spareParts) {
	    int totalUnits = wg.getUdsOfSparePart(sparePart.id);
	    sparePartsReport.add(DtoMapper.toSparePartReportDto(sparePart, totalUnits));
	}

	return sparePartsReport;
    }

}
