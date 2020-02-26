package simple.y2020.m02.d26;

import java.util.Random;

public class Draw20200226
{
	
	String empty =" ";
	String item = "#";
	public void draw(int height,int count,int width)
	{		
		Random random = new Random();
		
		int index = width/2;
		
		for(int i =0;i<width;i++)
		{
			for(int j=0;j<height;j++)
			{
				if(j==index)
				{
					System.out.print(item+item);
				}
				else {
					System.out.print(empty+empty);
				}
				
			}
			System.out.println();
			index += random.nextInt(2)-1;
		}
		
		
	}
	
	public static void main(String[] args)
	{
		Draw20200226 draw = new Draw20200226();
		draw.draw(3, 20, 10);
	}
}
