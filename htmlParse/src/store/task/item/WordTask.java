package store.task.item;

import log.Logger;
import store.unit.IBaseStoreUnit;
import store.unit.WordUnit;

public class WordTask extends IBaseTask<WordUnit>
{

	
	Logger log = new Logger(this.getClass());
	@Override
	public void execute(WordUnit unit)
	{
		unit.persist();
		log.log("存储word内容至文件",unit.getFileName());
		
	}

}
