package simple.y2020;

public class DrawUtil {

	public static void show(String[][] items,int margin)
	{
		for(String[] item : items)
		{
			for(int i=0;i<margin;i++) {
				System.out.print(" ");
			}
			for(String str : item)
			{
				System.out.print(str);
			}
			System.out.println();
		}
		
	}
	
	public static void initItem(String[][] items,String empty)
	{
		for(int i=0;i<items.length;i++)
		{
			for(int j=0;j<items[i].length;j++)
			{
				items[i][j] = empty;
			}
		}
	}
}
