package oper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import html.HtmlOper;
import javafx.scene.input.KeyCode;

public class GroupTask implements Runnable
{
	ThreadLocal<GroupItem>  local;
	
	public void setItem(GroupItem item)
	{
		this.local.set(item);;
	}

	@Override
	public void run()
	{
		System.out.println("开始执行操作");
		
		GroupItem item = local.get();
		
		for(SourceItem sitem : item.getSources())
		{
			ChromeOptions options = new ChromeOptions();
			
			WebDriver driver  =null;

			if(item.getShow()==1)
			{
				driver = new ChromeDriver();
			}else if(item.getShow()==2)
			{
				Map<String, String> mobileEmulation = new HashMap<String, String>();
				mobileEmulation.put("deviceName", "Galaxy S5");
				options.setExperimentalOption("mobileEmulation", mobileEmulation);
				driver = new ChromeDriver(options);
			}
			else {
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				driver = new ChromeDriver(options); 
			}
			driver.manage().window().maximize();//浏览器最大化
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//超时等待30秒
			
	    	driver.get(sitem.getSrc());
		    System.out.println("加载页面:"+ sitem.getSrc());
		    
		    for(int i=0;i<item.getOperCount();i++)
			{
		    	WebElement body = driver.findElement(By.tagName("body"));
			    for(OperItem oitem:item.getOpers())
			    {
			    	try {
					    By by =null;
					    for(SimpleItem select : oitem.getItems())
					    {
					    	by= getDom(select.getKey(),select.getValue(),by);
					    }
				    	WebElement ele = body;
				    	if(by!=null)
				    	{	 
				    		ele= body.findElement(by);
				    	}
				    	oper(oitem,ele,driver,item.getInterval());
			    	}
			    	catch(Exception e)
			    	{
			    		e.printStackTrace();
			    	}
			    	 
			    	try
					{
						Thread.sleep(item.getInterval()*1000);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}
			
		}
		System.out.println("完成执行操作");
		
	}
	
	
	public By getDom(String key,String value,By by)
	{
		if(by==null)
		{
			switch(key) {
				case "class":by =By.className(value);break;
				case "name":by =By.name(value);break;
				case "id":by =By.id(value);break;
				case "tag":by =By.tagName(value);break;
			}
			
		}
		else{
			switch(key) {
				case "class":by =by.className(value);break;
				case "name":by =by.name(value);break;
				case "id":by =by.id(value);break;
				case "tag":by =by.tagName(value);break;
			}
		
		}
		return by;
	}
		
	public void oper(OperItem item,WebElement ele,WebDriver driver,int interval)
	{
		Actions actions=new Actions(driver);
		HtmlOper oper = new HtmlOper();
		switch(item.getType()) {
			case "click":{
				System.out.println("执行点击操作");actions.click(ele).perform();break;
			}
			case "dbclick":{
				System.out.println("执行双击操作");actions.doubleClick(ele).perform();break;
			}
			case "rightclick":{
				System.out.println("执行右击操作");actions.contextClick(ele).perform();break;
			}
			case "input":{
				System.out.println("执行输入操作");ele.clear();ele.sendKeys(item.getValue());break;
			}
			case "move":{
				System.out.println("执行输入操作");actions.moveToElement(ele).perform();break;
			}
			case "close":{
				System.out.println("执行关闭操作");driver.quit();break;
			}
			case "img":{
				System.out.println("执行下载图片操作");oper.downLoadImg(new File(item.getValue()), ele);break;
			}
			case "video":{
				System.out.println("执行下载视频操作");oper.downLoadImg(new File(item.getValue()), ele);break;
			}
			case "text":{
				System.out.println("执行获取文本操作");oper.downLoadText(new File(item.getValue()), ele);break;
			}
			case "html":{
				System.out.println("执行获取内容操作");oper.downLoadHtml(new File(item.getValue()), ele);break;
			}
			case "wait":System.out.println("执行等待操作");
						try 
						{
							Thread.sleep(Integer.valueOf(item.getValue())*1000);
						} catch (NumberFormatException | InterruptedException e)
						{
							e.printStackTrace();
						}break;
			case "scroll":{
				System.out.println("执行滚屏操作");
				int old = item.getEnd();
				if(item.getEnd()==-1)
				{
					if(ele==null)
					{
						ele = driver.findElement(By.tagName("body"));
					}

					item.setEnd(ele.getSize().height);
				}
				if(item.getEnd()<=item.getBegin())break;
				
				ele.click();
				
				int wait = item.getEnd()/item.getInterval() + 1;
				for(int i=0;i<wait;i++)
				{
					actions.sendKeys(Keys.DOWN).build().perform();
//					System.out.println(i);
					try
					{
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				item.setEnd(old);
				break;
			}
		}
	}

}
