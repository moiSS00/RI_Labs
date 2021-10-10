package uo.ri.cws.application.ui.manager;

import alb.util.menu.BaseMenu;
import uo.ri.cws.application.ui.manager.mechanic.MechanicsMenu;
import uo.ri.cws.application.ui.manager.spares.SparePartsManagementMenu;
import uo.ri.cws.application.ui.manager.vehicletype.VehicleTypesMenu;

public class MainMenu extends BaseMenu {
	
	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Administrator", null },
			{ "Mechanics management", 			MechanicsMenu.class }, 
			{ "Spare parts management", 			SparePartsManagementMenu.class },
			{ "Vehicle types management", 	VehicleTypesMenu.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}