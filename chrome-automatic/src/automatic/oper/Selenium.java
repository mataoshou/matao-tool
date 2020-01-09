package automatic.oper;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Selenium
{
	public static void main(String[] args)
	{
		System.out.println("start parse");
		String path =  Selenium.class.getClass().getResource("/").getPath();
		
		File driveFile = new File(path,"chromedriver.exe");
		System.out.println(path);
		
		System.setProperty("webdriver.chrome.driver",driveFile.getPath());
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		
        //获得Firefox浏览器的控制权
        //WebDriver driver = new FirefoxDriver();
        //谷歌浏览器驱动
        WebDriver driver = new ChromeDriver();
        //IE浏览器驱动
        //WebDriver driver = new InternetExplorerDriver();
        
        driver.manage().window().maximize();//浏览器最大化
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);//超时等待30秒
        String[] urls = new String[] {
        	"https://www.kuaikanmanhua.com/web/comic/191612/",
        	"https://www.kuaikanmanhua.com/web/comic/196449/",
        	"https://www.kuaikanmanhua.com/web/comic/201231/"
        };
        for(String sourceSrc : urls)
        {
	        //向浏览器发送网址
	        driver.get(sourceSrc);
	        System.out.println("加载页面:"+ sourceSrc);
	        try
			{
				Thread.sleep(1000*5);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	        WebElement body = driver.findElement(By.tagName("body"));
	        int height = 900;
	        System.out.println("页面总高度 :" + body.getSize().height);
	        while(height<body.getSize().height)
	        {
		        String js="var q=document.documentElement.scrollTop=" +height;
		        ((JavascriptExecutor) driver).executeScript(js);
		        System.out.println("页面翻到:" +height);
		        height +=900;
		        try
				{
					Thread.sleep(1000*5);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
	        }

	        System.out.println(body.getAttribute("innerHTML"));
	        
	        List<WebElement> eles = driver.findElements(By.tagName("img").className("img"));
	        
	        for(WebElement ele :eles)
	        {
	        	try {
		        	String src = ele.getAttribute("src");
		        	System.out.println(src);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	        System.out.println("end parse");
        }
        driver.quit();
		
	}
}
