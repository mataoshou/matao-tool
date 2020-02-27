package simple.y2020.m02.d26;

import java.util.Random;

public class Draw20200226
{
	
	String empty =" ";
	String item = "#";
	
	int interval =1;
	String[][] items;
	public void draw(int height,int count)
	{
		int width = height*count*2;
		items = new String[height*count][width];
		initItem(items);
		draw(width/2,0,height,0);
//		draw(width/2,1,height,1);
//		draw(width/2,1,height,-1);
		show(items);
	}
	
	public void initItem(String[][] items)
	{
		for(int i=0;i<items.length;i++)
		{
			for(int j=0;j<items[i].length;j++)
			{
				items[i][j] = empty;
			}
		}
	}
	
	public void draw(int index, int level, int height,int interval)
	{
		System.out.println(String.format("%s.....%s......%s",index,level,height));
		for(int i=0;i<height;i++)
		{
			index +=interval;
			items[level*height+i][index] = item;
		}
		level++;
		if((level*height+1)<items.length)
		{
			if(interval==0)interval=1;
			draw(index, level, height,interval);
			draw(index, level, height,interval*-1);
		}
	}
	
	public void show(String[][] items)
	{
		for(String[] item : items)
		{
			for(String str : item)
			{
				System.out.print(str);
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args)
	{
		Draw20200226 draw = new Draw20200226();
		draw.draw(3, 5);
	}
}
