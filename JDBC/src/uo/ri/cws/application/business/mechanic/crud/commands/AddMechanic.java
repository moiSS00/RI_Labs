package uo.ri.cws.application.business.mechanic.crud.commands;

import java.sql.SQLException;
import java.util.UUID;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class AddMechanic implements Command<MechanicDto> {

    private MechanicDto mechanic;

    public AddMechanic(MechanicDto mechanic) {
	this.mechanic = mechanic;
    }

    public MechanicDto execute() throws BusinessException, SQLException {

	MechanicGateway mg = PersistenceFactory.forMechanic();
	validateMechanic();

	BusinessCheck.isTrue(mg.findByDni(mechanic.dni).isEmpty(),
		"There already exist another mechanic with the same dni");
	mechanic.id = UUID.randomUUID().toString();
	mg.add(DtoMapper.toRecord(mechanic));

	return mechanic;
    }

    /*
     * Valida el mecanico
     */
    private void validateMechanic() {
	Argument.isNotNull(mechanic.dni, "The mechanic can not be empty");
	Argument.isNotEmpty(mechanic.dni, "DNI can not be empty");
	Argument.isNotEmpty(mechanic.name, "Name can not be empty");
	Argument.isNotEmpty(mechanic.surname, "Surname can not be empty");
    }

}
