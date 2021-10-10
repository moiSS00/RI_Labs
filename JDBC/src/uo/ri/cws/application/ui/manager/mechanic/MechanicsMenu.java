package uo.ri.cws.application.ui.manager.mechanic;

import alb.util.menu.BaseMenu;
import uo.ri.cws.application.ui.manager.mechanic.action.AddMechanicAction;
import uo.ri.cws.application.ui.manager.mechanic.action.DeleteMechanicAction;
import uo.ri.cws.application.ui.manager.mechanic.action.FindAllMechanicsAction;
import uo.ri.cws.application.ui.manager.mechanic.action.UpdateMechanicAction;

public class MechanicsMenu extends BaseMenu {

	public MechanicsMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Mechanics management", null},
			
			{ "Add mechanic", 				AddMechanicAction.class }, 
			{ "Update mechanic", 	UpdateMechanicAction.class }, 
			{ "Delete mechanic", 				DeleteMechanicAction.class }, 
			{ "List mechanics", 				FindAllMechanicsAction.class },
		};
	}

}
