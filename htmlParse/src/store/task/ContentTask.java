package store.task;

import store.unit.IBaseStoreUnit;

public class ContentTask extends IBaseTask
{

	@Override
	public void execute(IBaseStoreUnit unit)
	{
		unit.persist();
	}

}
