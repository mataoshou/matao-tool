package store;

public class StoreItem
{
	//32字节  前8存储文件id  12位存储时间戳
	private long id;
	
	private String name;
	
	private long begin;
	private long end;
	private long length;
	
	private int fileNo;
	
	//内容加密 字段和值用=分割 字段间用&分割  进行三次加密
	private String content;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getBegin()
	{
		return begin;
	}

	public void setBegin(long begin)
	{
		this.begin = begin;
	}

	public long getEnd()
	{
		return end;
	}

	public void setEnd(long end)
	{
		this.end = end;
	}

	public long getLength()
	{
		return length;
	}

	public void setLength(long length)
	{
		this.length = length;
	}

	public int getFileNo()
	{
		return fileNo;
	}

	public void setFileNo(int fileNo)
	{
		this.fileNo = fileNo;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

}
