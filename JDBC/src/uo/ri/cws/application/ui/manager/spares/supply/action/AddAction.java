package uo.ri.cws.application.ui.manager.spares.supply.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.supply.SuppliesCrudService;
import uo.ri.cws.application.business.supply.SupplyDto;

public class AddAction implements Action {

	@Override
	public void execute() throws Exception {
		SupplyDto dto = new SupplyDto();
		
		Console.println("Please, provide the following data:");
		dto.provider.nif = Console.readString("Provider nif: ");
		dto.sparePart.code = Console.readString("Spare part code: ");
		dto.price = Console.readDouble("Price: ");
		dto.deliveryTerm = Console.readInt("Delivery term: ");
		
		SuppliesCrudService service = BusinessFactory.forSuppliesCrudService();
		String id = service.add(dto);
		
		Console.println("The new supply has been registered with id " +  id);
	}

}
