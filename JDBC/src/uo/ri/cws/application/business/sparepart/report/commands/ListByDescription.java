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

public class ListByDescription implements Command<List<SparePartReportDto>> {

    private String description;

    public ListByDescription(String description) {
	this.description = description.toUpperCase();
    }

    public List<SparePartReportDto> execute() throws SQLException {
	SparePartGateway sg = PersistenceFactory.forSparePart();
	WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
	List<SparePartRecord> spareParts = sg.findByDescription(description);
	List<SparePartReportDto> result = new ArrayList<SparePartReportDto>();
	for (SparePartRecord sp : spareParts) {
	    int totalUnits = wg.getUdsOfSparePart(sp.id);
	    result.add(DtoMapper.toSparePartReportDto(sp, totalUnits));
	}
	return result;
    }

}
