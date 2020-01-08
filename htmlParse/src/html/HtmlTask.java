package html;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import util.DownImage;
import util.FileStore;

public class HtmlTask extends Thread
{

	@Override
	public void run()
	{
		try
		{
			getHtml("http://www.u17.com/chapter/791220.html#image_id=5787057");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
				
	}
	
	
	public void getHtml(String url) throws IOException
	{
		
		// HtmlUnit 模拟浏览器
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
        webClient.getOptions().setRedirectEnabled(false);  
        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持AJAX
                    
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(60 * 1000); // 等待js后台执行30秒
        String pageAsXml = page.asXml();
        
        webClient.close();
        
        // Jsoup解析处理
        Document doc = Jsoup.parse(pageAsXml, url);  
        Elements pngs = doc.select("img");                   // 获取所有图片元素集
        
        
        // 此处省略其他操作
        System.out.println(pageAsXml);
        
        File file = new File("c:/123.html");
        FileStore.putString(file, pageAsXml);
        
        System.out.println("............................." +pngs.size()); 
        DownImage down = new DownImage();
        File root = new File(String.format("c:/111/%s/",System.currentTimeMillis()/1000));
        root.mkdirs();
        for(int i=0;i<pngs.size();i++)
        {
        	try {
	        	System.out.println(".......................................");
	        	String src = pngs.get(i).attr("src").trim();
	        	System.out.println(pngs.get(i).toString());
	        	System.out.println(src);
	        	if(src!=null&&src.trim().length()>0)
	        	{
	        		down.saveToFile(src, new File(root,String.format("%s.jpg", i)));
	        	}
	        	
	        	System.out.println(".......................................");
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	
        }
	}
	
	
	
	public static void main(String[] args)
	{
		HtmlTask task = new HtmlTask();
		task.start();
	}

	
	
}
