package oper;

import java.util.ArrayList;
import java.util.List;

public class OperItem
{
	private String name;
	
	private String type;
	
	private String value;
	
	private int begin;
	
	private int end;
	
	private int interval;
	
	private List<SimpleItem> items = new ArrayList();
	
	
	
	public int getBegin()
	{
		return begin;
	}

	public void setBegin(int begin)
	{
		this.begin = begin;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public void addItem(SimpleItem item)
	{
		items.add(item);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public List<SimpleItem> getItems()
	{
		return items;
	}

	public void setItems(List<SimpleItem> items)
	{
		this.items = items;
	}
	
	
	
	
	
}
