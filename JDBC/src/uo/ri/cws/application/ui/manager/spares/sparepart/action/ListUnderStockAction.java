package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.sparepart.SparePartReportService;
import uo.ri.cws.application.ui.util.Printer;

public class ListUnderStockAction implements Action {

	@Override
	public void execute() throws Exception {
		SparePartReportService service = BusinessFactory.forSparePartReportService();
		List<SparePartReportDto> spares = service.findUnderStock();
		
		Console.println("There are " + spares.size() + " spares under stock");
		for(SparePartReportDto sp: spares) {
			Printer.print(sp);
		}
	}

}
