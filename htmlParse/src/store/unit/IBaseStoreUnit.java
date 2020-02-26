package store.unit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Map;

import log.Logger;

public abstract class IBaseStoreUnit<T>
{
	
	T item;
	
	Logger log = new Logger(this.getClass());
	
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
	
	/**
	 * 存储
	 */
	public abstract void persist();
	
	public abstract int getLength();
	
	public abstract String getFileId();
	
	
	public abstract void readItem(RandomAccessFile stream,String fileId) throws Exception;
	
}
