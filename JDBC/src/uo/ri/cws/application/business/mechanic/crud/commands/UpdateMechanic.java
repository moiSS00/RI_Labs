package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto mechanic;

    public UpdateMechanic(MechanicDto mechanic) {
	this.mechanic = mechanic;
    }

    public Void execute() throws BusinessException, SQLException {

	MechanicGateway mg = PersistenceFactory.forMechanic();
	validateMechanic();

	BusinessCheck.isFalse(mg.findById(mechanic.id).isEmpty(), "The mechanic does not exist");
	mg.update(DtoMapper.toRecord(mechanic));

	return null;
    }

    /*
     * Valida el mecanico
     */
    private void validateMechanic() {
	Argument.isNotNull(mechanic, "The mechanic can not be empty");
	Argument.isNotEmpty(mechanic.id, "ID can not be empty");
	Argument.isNotEmpty(mechanic.name, "Name can not be empty");
	Argument.isNotEmpty(mechanic.surname, "Surname can not be empty");
    }
}
