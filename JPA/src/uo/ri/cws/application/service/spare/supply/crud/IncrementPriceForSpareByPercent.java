package uo.ri.cws.application.service.spare.supply.crud;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;

public class IncrementPriceForSpareByPercent implements Command<Void>{
	
	private String code; 
	private double percentage; 
	private SupplyRepository repoSu = Factory.repository.forSupply();
	private SparePartRepository repoSp = Factory.repository.forSparePart(); 
	
	public IncrementPriceForSpareByPercent(String code, double percentage) {
		this.code = code; 
		this.percentage = percentage; 
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessChecks.isTrue(repoSp.findByCode(code).isPresent(),"The sparePart does not exists");
		List<Supply> supplies = repoSu.findBySparePartCode(code);
		for(Supply supply : supplies) {
			supply.increasePriceBy(percentage);
		}
		return null;
	}

}
