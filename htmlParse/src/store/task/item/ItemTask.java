package store.task.item;

import log.Logger;
import store.unit.ContentUnit;
import store.unit.IBaseStoreUnit;
import store.unit.ItemUnit;

public class ItemTask extends IBaseTask<ItemUnit>
{
	Logger log = new Logger(this.getClass());
	@Override
	public void execute(ItemUnit unit)
	{
		unit.persist();
		log.log("存储item内容至文件",unit.getFileName());
	}

}
