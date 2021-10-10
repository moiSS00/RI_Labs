package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;
import java.util.UUID;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class Add implements Command<String> {

    private SparePartDto sparePart;

    public Add(SparePartDto sparePart) {
	this.sparePart = sparePart;
    }

    public String execute() throws SQLException, BusinessException {
	SparePartGateway spw = PersistenceFactory.forSparePart();

	validateSparePart();
	BusinessCheck.isTrue(spw.findByCode(sparePart.code).isEmpty(),
		"There already exist another spare part with the same code");
	BusinessCheck.isFalse(sparePart.code == null, "The code can not be null");
	BusinessCheck.isFalse(sparePart.description == null, "The description can not be null");
	BusinessCheck.isFalse(sparePart.code.isEmpty(), "The code can not be empty");
	BusinessCheck.isFalse(sparePart.description.isEmpty(), "The description can not be empty");
	BusinessCheck.isTrue(sparePart.stock >= 0, "The stock must be >= 0");
	BusinessCheck.isTrue(sparePart.minStock >= 0, "The min stock must be >= 0");
	BusinessCheck.isTrue(sparePart.maxStock >= 0, "The max stock must be >= 0");
	BusinessCheck.isTrue(sparePart.price >= 0, "The price must be >= 0");
	BusinessCheck.isTrue(sparePart.maxStock > sparePart.minStock, "The max stock must be > min stock");

	String id = UUID.randomUUID().toString();
	sparePart.id = id;
	sparePart.description = sparePart.description.toUpperCase();
	spw.add(DtoMapper.toRecord(sparePart));
	return id;
    }

    /*
     * Valida la informacion del repuesto
     */
    private void validateSparePart() {
	Argument.isNotNull(sparePart);
	Argument.isNotNull(sparePart.stock);
	Argument.isNotNull(sparePart.minStock);
	Argument.isNotNull(sparePart.maxStock);
	Argument.isNotNull(sparePart.price);
    }
}
