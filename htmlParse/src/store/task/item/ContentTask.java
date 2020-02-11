package store.task.item;

import log.Logger;
import store.unit.ContentUnit;
import store.unit.IBaseStoreUnit;

public class ContentTask extends IBaseTask<ContentUnit>
{

	Logger log = new Logger(this.getClass());
	@Override
	public void execute(ContentUnit unit)
	{
		unit.persist();
		log.log("存储content内容至文件",unit.getFileName());
	}

}
