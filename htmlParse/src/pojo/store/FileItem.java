package pojo.store;

public class FileItem
{
	private int id;
	
	private String filePath;
	
	private String fileName;
	
	private long max;
	
	private long used;
	
	private long empty;
	
	private int storeCount;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
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
