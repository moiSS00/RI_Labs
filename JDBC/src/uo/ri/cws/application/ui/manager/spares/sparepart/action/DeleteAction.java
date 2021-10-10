package uo.ri.cws.application.ui.manager.spares.sparepart.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.sparepart.SparePartCrudService;

public class DeleteAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Code: ");
		
		SparePartCrudService service = BusinessFactory.forSparePartCrudService();
		service.delete(code);

		Console.println("The spare part has been deleted");
	}

}
