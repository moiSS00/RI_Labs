package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllMechanics implements Command<List<MechanicDto>> {

	public List<MechanicDto> execute() {
		MechanicRepository repo = Factory.repository.forMechanic();
		return DtoAssembler.toMechanicDtoList(repo.findAll());
	}

}
