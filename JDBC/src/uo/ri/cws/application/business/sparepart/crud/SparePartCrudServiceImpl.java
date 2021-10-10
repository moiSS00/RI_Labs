package uo.ri.cws.application.business.sparepart.crud;

import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.sparepart.SparePartCrudService;
import uo.ri.cws.application.business.sparepart.SparePartDto;
import uo.ri.cws.application.business.sparepart.crud.commands.Add;
import uo.ri.cws.application.business.sparepart.crud.commands.Delete;
import uo.ri.cws.application.business.sparepart.crud.commands.FindByCode;
import uo.ri.cws.application.business.sparepart.crud.commands.Update;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class SparePartCrudServiceImpl implements SparePartCrudService {

    private CommandExecutor excutor = new CommandExecutor();

    @Override
    public String add(SparePartDto dto) throws BusinessException {
	Add add = new Add(dto);
	return excutor.execute(add);
    }

    @Override
    public void delete(String code) throws BusinessException {
	Delete delete = new Delete(code);
	excutor.execute(delete);

    }

    @Override
    public void update(SparePartDto dto) throws BusinessException {
	Update update = new Update(dto);
	excutor.execute(update);

    }

    @Override
    public Optional<SparePartDto> findByCode(String code) throws BusinessException {
	FindByCode fbc = new FindByCode(code);
	return excutor.execute(fbc);
    }

}
