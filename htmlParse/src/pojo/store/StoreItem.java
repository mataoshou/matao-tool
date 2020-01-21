package pojo.store;

import java.util.HashMap;
import java.util.Map;

public class StoreItem
{
	//32字节  前4存储文件id  12位存储时间戳   16 字符 md5
	private String id;
	
	private String name;
	
	//
	private long begin;
	private long end;
	private int length;
	
	private String fileName;
	
	//32字节  存储文件名称
	private String storeName;

	private Map<String,String> content = new HashMap();
	
	private int mod = 0;
	
	
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	
	
	public int getMod()
	{
		return mod;
	}

	public void setMod(int mod)
	{
		this.mod = mod;
	}

	public String getStoreName()
	{
		return storeName;
	}

	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
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

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Map<String, String> getContent()
	{
		return content;
	}

	public void setContent(Map<String, String> content)
	{
		this.content = content;
	}
	
	public void addContent(String key ,String value)
	{
		this.content.put(key, value);
	}

}
