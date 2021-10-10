package uo.ri.cws.application.business.sparepart.report;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartReportDto;
import uo.ri.cws.application.business.sparepart.SparePartReportService;
import uo.ri.cws.application.business.sparepart.report.commands.ListByDescription;
import uo.ri.cws.application.business.sparepart.report.commands.ListUnderStock;
import uo.ri.cws.application.business.sparepart.report.commands.ViewDetail;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class SparePartReportImpl implements SparePartReportService {

    CommandExecutor executor = new CommandExecutor();

    @Override
    public Optional<SparePartReportDto> findByCode(String code) throws BusinessException {
	ViewDetail vd = new ViewDetail(code);
	return executor.execute(vd);
    }

    @Override
    public List<SparePartReportDto> findByDescription(String description) throws BusinessException {
	ListByDescription ld = new ListByDescription(description);
	return executor.execute(ld);
    }

    @Override
    public List<SparePartReportDto> findUnderStock() throws BusinessException {
	ListUnderStock lus = new ListUnderStock();
	return executor.execute(lus);
    }

}
