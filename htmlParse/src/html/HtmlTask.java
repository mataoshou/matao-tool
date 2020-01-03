package html;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import util.FileStore;

public class HtmlTask extends Thread
{

	@Override
	public void run()
	{
		try
		{
			getHtml("https://www.baidu.com/s?wd=%E6%B1%9F%E8%8B%8F%E7%9C%81%E8%80%83%E6%88%90%E7%BB%A9");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
				
	}
	
	
	public void getHtml(String url) throws IOException
	{
//		Document doc = Jsoup.connect(url).get();
//		
//		String htmlContent = doc.html();
//		
//		System.out.println(htmlContent);
		
		// HtmlUnit 模拟浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
        webClient.getOptions().setRedirectEnabled(true);  
        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
        HtmlPage page = webClient.getPage(url);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置支持AJAX
        webClient.waitForBackgroundJavaScript(60 * 1000);               // 等待js后台执行30秒
        
        String pageAsXml = page.asXml();
        
        // Jsoup解析处理
        Document doc = Jsoup.parse(pageAsXml, url);  
        Elements pngs = doc.select("a");                   // 获取所有图片元素集
        
        
        // 此处省略其他操作
        System.out.println(pageAsXml);
        
        File file = new File("c:/123.html");
        FileStore.putString(file, pageAsXml);
        
        System.out.println("............................." +pngs.size()); 
        
        for(int i=0;i<pngs.size();i++)
        {
        	System.out.println(".......................................");
        	String src = pngs.get(i).attr("href").trim();
        	System.out.println(pngs.get(i).toString());
        	System.out.println(src);
        	System.out.println(".......................................");
        	
        }
	}
	
	
	
	public static void main(String[] args)
	{
		HtmlTask task = new HtmlTask();
		task.start();
	}

	
	
}
