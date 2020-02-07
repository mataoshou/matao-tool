package log;

public class Logger {
	
	Class cl;
	
	public Logger(Class cl)
	{
		this.cl = cl;
	}
	
	/**
	 * 打印日志
	 * @param strings
	 */
	public void log(String...strings)
	{
		System.out.println(getLog(strings));
	}
	
	/**
	 * 构建日志
	 * @param strings
	 * @return
	 */
	public String getLog(String...strings)
	{
		String logContent = "";
		
		logContent +=String.format(" [%s] ", cl.getSimpleName());
		
		for(String s :strings)
		{
			logContent += String.format(" [%s] ", s);
		}
		
		return logContent;
	}
}
