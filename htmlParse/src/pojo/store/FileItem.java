package pojo.store;

import store.task.cache.FileCache;

public class FileItem
{
	//唯一标识
	private String id;
	
	private String fileName;
	//最大存储空间
	private long max;
	//已使用存储
	private long used;
	//空闲存储
	private long empty;
	//存储数量
	private int storeCount;
	//文件是否存在  0 不存在  1存在
	private int isExit = 1;
	
	private long reserve =0;
	
	//word  item  content
	private String type;
	
	public void addReserve(int space)
	{
		reserve += space;
		
		FileCache.single().modAdd();
	}
	
	public long getReserve() {
		return reserve;
	}



	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	

	public int getIsExit()
	{
		return isExit;
	}

	public void setIsExit(int isExit)
	{
		this.isExit = isExit;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public long getMax()
	{
		return max;
	}

	public void setMax(long max)
	{
		this.max = max;
	}

	public long getUsed()
	{
		return used;
	}

	public void setUsed(long used)
	{
		this.used = used;
	}

	public long getEmpty()
	{
		return empty;
	}

	public void setEmpty(long empty)
	{
		this.empty = empty;
	}

	public int getStoreCount()
	{
		return storeCount;
	}

	public void setStoreCount(int storeCount)
	{
		this.storeCount = storeCount;
	}
}
