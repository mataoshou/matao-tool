package store.unit;

import java.io.File;
import java.util.Map;

public abstract class IBaseStoreUnit<T>
{
	
	T item;
	
	int idLen = 32;//id长度
	int noLen = 8;//数字长度
	int stateLen =1;//状态长度
	
	public void setItem(T t)
	{
		this.item = t;
	}
	
	public T getItem()
	{
		return item;
	}
	
	public abstract String buildFileContent();
	
	
	public abstract long getBegin();
	
	
	public abstract String getFileName();
	
	
	public abstract boolean isFull();
	
	
	public abstract String getId();
	
	
	public abstract void persist();
	
}
