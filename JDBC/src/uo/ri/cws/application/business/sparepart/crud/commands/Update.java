package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.DtoMapper;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;

public class Update implements Command<Void> {

    private SparePartDto sparePart;

    public Update(SparePartDto sparePart) {
	this.sparePart = sparePart;
    }

    public Void execute() throws SQLException, BusinessException {
	SparePartGateway spw = PersistenceFactory.forSparePart();

	validateSparePart();
	BusinessCheck.isFalse(spw.findByCode(sparePart.code).isEmpty(), "The spare part does not exist");
	BusinessCheck.isFalse(sparePart.code == null, "The code can not be null");
	BusinessCheck.isFalse(sparePart.description == null, "The description can not be null");
	BusinessCheck.isFalse(sparePart.id.isEmpty(), "The id can not be empty");
	BusinessCheck.isFalse(sparePart.description.isEmpty(), "The description can not be empty");
	BusinessCheck.isTrue(sparePart.stock >= 0, "The stock must be >= 0");
	BusinessCheck.isTrue(sparePart.minStock >= 0, "The min stock must be >= 0");
	BusinessCheck.isTrue(sparePart.maxStock >= 0, "The max stock must be >= 0");
	BusinessCheck.isTrue(sparePart.price >= 0, "The price must be >= 0");
	BusinessCheck.isTrue(sparePart.maxStock > sparePart.minStock, "The max stock must be > min stock");

	sparePart.description = sparePart.description.toUpperCase();
	spw.update(DtoMapper.toRecord(sparePart));
	return null;
    }

    /*
     * Valida la informacion del repuesto
     */
    private void validateSparePart() {
	Argument.isNotNull(sparePart);
	Argument.isNotNull(sparePart.id);
	Argument.isNotNull(sparePart.stock);
	Argument.isNotNull(sparePart.minStock);
	Argument.isNotNull(sparePart.maxStock);
	Argument.isNotNull(sparePart.price);
    }

}
