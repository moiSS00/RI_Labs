package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;
import java.util.Optional;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.sparepart.SparePartRecord;

public class FindByCode implements Command<Optional<SparePartDto>> {

    private String code;

    public FindByCode(String code) {
	this.code = code;
    }

    public Optional<SparePartDto> execute() throws BusinessException {
	SparePartGateway sg = PersistenceFactory.forSparePart();
	validateCode(code);

	try {

	    Optional<SparePartRecord> sprecord = sg.findByCode(code);

	    BusinessCheck.isFalse(sprecord.isEmpty(), "The spare part does not exist");

	    Optional<SparePartDto> spdto = sprecord.isEmpty() ? Optional.ofNullable(null)
		    : Optional.ofNullable(DtoMapper.toDto(sprecord.get()));

	    return spdto;

	} catch (SQLException e) {
	    throw new RuntimeException("Connection error");
	}
    }

    private void validateCode(String code) {
	Argument.isNotEmpty(code);
    }
}
