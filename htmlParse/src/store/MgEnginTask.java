package store;

public class MgEnginTask extends Thread
{

	@Override
	public void run()
	{
		while(true)
		{
		
			try {
				PoolEngin.single().startWork();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			try
			{
				Thread.sleep(1000*5);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	
	
}
