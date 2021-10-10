package uo.ri.cws.application.ui.manager.mechanic.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.ui.util.Printer;

public class FindAllMechanicsAction implements Action {
	
	@Override
	public void execute() throws BusinessException {

		Console.println("\nList of mechanics \n");  
		Printer.printMechanics(
				BusinessFactory.forMechanicCrudService().findAllMechanics()); 

	}
}
