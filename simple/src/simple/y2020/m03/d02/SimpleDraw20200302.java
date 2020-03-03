package simple.y2020.m03.d02;

import simple.y2020.DrawUtil;

public class SimpleDraw20200302 {
	String empty = " ";
	String item = "#";
	public void simpledraw(int length,int margin)
	{
		String[][] items = new String[length*3][length*3];
		DrawUtil.initItem(items, empty);
		for(int i=0;i<length;i++)
		{
			if(i==0)
			{
				for(int j=0;j<length;j++)
				{
					items[i][j+length] = item;
				}
			}
			else {
				items[i][length-i] = item;
				items[i][length*2+i] = item;
			}
		}
		for(int i=0;i<length;i++)
		{
			if(i==length-1)
			{
				for(int j=0;j<length;j++)
				{
					items[i+length][j+length] = item;
				}
			}
			else {
				items[i+length][i] = item;
				items[i+length][length*3-i-1] = item;
			}
		}
		
		DrawUtil.show(items,margin);
	}
	
	
	public static void main(String[] args)
	{
		SimpleDraw20200302 draw = new SimpleDraw20200302();
		draw.simpledraw(10,5);
	}
}
