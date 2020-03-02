package simple.y2020.m02.d27;

public class Draw20200227 {
	
	String item = "#";
	String empty=" ";
	public void draw(int width,int height,int margin,int interval)
	{
		
		for(int i=0;i<height;i++)
		{
			for(int m=0;m<margin;m++)
			{
				System.out.print(empty);
			}
			
			for(int j=0;j<width;j++)
			{
				if(i%interval==0)
				{
					System.out.print(item);
				}else if(j%interval==0)
				{
					System.out.print(item);
				}else {
					System.out.print(empty);
				}
			}
			System.out.println();
		}
	}
	
	
	
	
	
	public static void main(String[] args)
	{
		Draw20200227 draw = new Draw20200227();
		draw.draw(16, 16, 5,5);
	}

}
