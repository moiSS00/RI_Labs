package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class DeleteMechanic implements Command<Void> {

    private String idMechanic;

    public DeleteMechanic(String idMechanic) {
	this.idMechanic = idMechanic;
    }

    public Void execute() throws BusinessException, SQLException {

	MechanicGateway mg = PersistenceFactory.forMechanic();
	validateMechanic();

	BusinessCheck.isFalse(mg.findById(idMechanic).isEmpty(), "The mechanic does not exist");
	mg.remove(idMechanic);

	return null;
    }

    /*
     * Valida el id introducido
     */
    private void validateMechanic() {
	Argument.isNotEmpty(idMechanic, "ID can not be empty");
    }

}
