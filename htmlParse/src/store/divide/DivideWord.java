package store.divide;

public class DivideWord {
	public String[] divide(String content)
	{
		int length = content.length();
		
		
		String[] result = new String[length];
		for(int i=0;i<length;i++)
		{
			result[i] = content.substring(i,i+1);
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		DivideWord d = new DivideWord();
		String[] results = d.divide("matao11231");
		
		for(String s : results)
		{
			System.out.println(s );
		}
		
		
	}
	
}
