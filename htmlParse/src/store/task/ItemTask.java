package store.task;

import store.unit.IBaseStoreUnit;

public class ItemTask extends IBaseTask
{

	@Override
	public void execute(IBaseStoreUnit unit)
	{
		unit.persist();
		
	}

}
