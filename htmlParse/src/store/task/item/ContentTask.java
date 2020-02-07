package store.task.item;

import log.Logger;
import store.unit.IBaseStoreUnit;

public class ContentTask extends IBaseTask
{

	Logger log = new Logger(this.getClass());
	@Override
	public void execute(IBaseStoreUnit unit)
	{
		unit.persist();
		log.log("存储content内容至文件",unit.getFileName());
	}

}
