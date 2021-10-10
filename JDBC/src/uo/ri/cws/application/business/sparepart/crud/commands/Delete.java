package uo.ri.cws.application.business.sparepart.crud.commands;

import java.sql.SQLException;

import alb.util.assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.sparepart.SparePartGateway;
import uo.ri.cws.application.persistence.supply.SupplyGateway;

public class Delete implements Command<Void> {

    private String code;

    public Delete(String code) {
	this.code = code;
    }

    public Void execute() throws SQLException, BusinessException {
	SparePartGateway spw = PersistenceFactory.forSparePart();
	SupplyGateway sg = PersistenceFactory.forSupply();
	validateSparePart();
	BusinessCheck.isFalse(spw.findByCode(code).isEmpty(), "The spare part does not exist");
	BusinessCheck.isFalse(spw.getUsedSparePartsIds().contains(code), "The spare part is involved in an order");
	BusinessCheck.isTrue(sg.findBySparePartId(spw.findByCode(code).get().id).isEmpty(),
		"The spare part has suppliers");
	BusinessCheck.isFalse(spw.getSparePartsInSubstitutions().contains(code), "The spare part has substitutions");
	spw.remove(code);
	return null;
    }

    /*
     * Valida el codigo que se paso
     */
    private void validateSparePart() {
	Argument.isNotEmpty(code);
    }

}
