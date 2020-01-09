package automatic.oper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class GroupTask implements Runnable
{
	
	GroupItem item;
	
	public void setItem(GroupItem item)
	{
		this.item = item;
	}

	@Override
	public void run()
	{
		System.out.println("开始执行操作");
		for(SourceItem sitem : item.getSources())
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			 WebDriver driver  =null;
			 
			 if(item.getShow()==1)
			 {
				 driver = new ChromeDriver();
			 }
			 else {
				 driver = new ChromeDriver(options); 
			 }
			 driver.manage().window().maximize();//浏览器最大化
		     driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//超时等待30秒
			 
		     driver.get(sitem.getSrc());
		     System.out.println("加载页面:"+ sitem.getSrc());
		     WebElement body = driver.findElement(By.tagName("body"));
		     
		     for(OperItem oitem:item.getOpers())
		     {
		    	 By by =null;
		    	 for(SimpleItem select : oitem.getItems())
		    	 {
		    		 if(by ==null)
		    		 {
		    			 by= getDom(select.getKey(),select.getValue(),by);
		    		 }
		    	 }
		    	 WebElement ele = body.findElement(by);
		    	 oper(oitem.getType(),oitem.getValue(),ele,driver);
		     }
		     driver.quit();
			
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
		
	public void oper(String type,String value,WebElement ele,WebDriver driver)
	{
		Actions actions=new Actions(driver);
		switch(type) {
			case "click":actions.click(ele).perform();break;
			case "dbclick":actions.doubleClick(ele).perform();break;
			case "rightclick":actions.contextClick(ele).perform();break;
			case "intput":ele.clear();ele.sendKeys(value);break;
			case "move":actions.moveToElement(ele).perform();break;
		}
	}

}
