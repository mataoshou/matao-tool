package simple.y2020.m02.d21;

public class d20200121_Simple {
	public void create(int no)
	{
		int count = no;
		
		for(int i=1;i<=count;i++)
		{
			System.out.println(buildLine(count,i));
		}
		
		for(int i=count;i>0;i--)
		{
			System.out.println(buildLine(count,i));
		}
	}
	
	String item ="-";
	
	String empty ="+";
	
	String init =""; 
	
	public String buildLine(int total,int length)
	{
		String result=init;
		
		for(int i=0;i<total;i++)
		{
			if(i<length)
			{
				result=item +result;;
			}
			else {
				result =empty+result;
			}
		}
		
		for(int i=0;i<total;i++)
		{
			if(i<length)
			{
				result=result+item;
			}
			else {
				result =result +empty;
			}
		}
		
		return result;
		
		
	}
	
	public static void main(String[] args)
	{
		d20200121_Simple simple = new d20200121_Simple();
		simple.create(10);
	}
}	
