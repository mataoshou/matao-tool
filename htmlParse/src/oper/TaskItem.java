package oper;

import java.util.ArrayList;
import java.util.List;

import pojo.oper.GroupItem;

public class TaskItem
{
	private String name;
	
	private int interval;
	
	private int sync = 1;
	
	private int show =0;
	
	private List<GroupItem> list = new ArrayList();

	public void addGroup(GroupItem item)
	{
		list.add(item);
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	

	public int getShow()
	{
		return show;
	}

	public void setShow(int show)
	{
		this.show = show;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public int getSync()
	{
		return sync;
	}

	public void setSync(int sync)
	{
		this.sync = sync;
	}

	public List<GroupItem> getList()
	{
		return list;
	}

	public void setList(List<GroupItem> list)
	{
		this.list = list;
	}
	
}
