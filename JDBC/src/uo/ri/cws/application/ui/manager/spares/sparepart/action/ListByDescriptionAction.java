package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.sparepart.SparePartReportService;
import uo.ri.cws.application.ui.util.Printer;

public class ListByDescriptionAction implements Action {

	@Override
	public void execute() throws Exception {
		String desc = Console.readString("Spare part description (may be partial): ");
		
		SparePartReportService service = BusinessFactory.forSparePartReportService();
		List<SparePartReportDto> spares = service.findByDescription( desc );
		
		Console.println("There are " + spares.size() + " spare parts");
		for(SparePartReportDto p: spares) {
			Printer.print(p);
		}
	}

}
