package store.task.cache;

import log.Logger;

public abstract class IBaseCacheTask<T> {
	
	Logger log = new Logger(this.getClass());
	
	protected abstract void execute();
	
	public abstract void addItem(T item);
	
	public int setInterval()
	{
		return 10*1000;
	}
	
	private long lastTime =0;
	
	public void setLastTime()
	{
		lastTime = System.currentTimeMillis();
	}
	
	
	/**
	 * 验证是否达到运行时间
	 * @return
	 */
	public boolean isHour()
	{
		if((lastTime + setInterval())<=System.currentTimeMillis())
		{
			return true;
		}
		
		return false;
	}
	
	
	public void run() {
		
		setLastTime();
		execute();
	} 
	
}
