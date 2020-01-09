package html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import util.DownImage;
import util.FileStore;

public class HtmlOper
{
	public void downLoadImg(File root,WebElement ele)
	{
		root.mkdirs();
		HtmlCollect collect = new HtmlCollect();
		List<WebElement> eles = new ArrayList();
		eles.add(ele);
		List<String> imgs = collect.getImgSrc(eles);
		DownImage down = new DownImage();
		for(String src : imgs)
		{
			try {
				String fileName = System.currentTimeMillis()+".jpg";
				File file  = new File(root,fileName);
				System.out.println("开始下载图片:"+src);
				down.saveToFile(src, file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	public void downLoadVideo(File root,WebElement ele)
	{
		root.mkdirs();
		HtmlCollect collect = new HtmlCollect();
		List<WebElement> eles = new ArrayList();
		eles.add(ele);
		List<String> imgs = collect.getImgSrc(eles);
		DownImage down = new DownImage();
		for(String src : imgs)
		{
			try {
				String fileName = System.currentTimeMillis()+".mp4";
				File file  = new File(root,fileName);
				System.out.println("开始下载视频:"+src);
				down.saveToFile(src, file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void downLoadText(File root,WebElement ele)
	{
		root.mkdirs();
		HtmlCollect collect = new HtmlCollect();
		String text = collect.getText(ele);
		String fileName = System.currentTimeMillis()+".txt";
		File file  = new File(root,fileName);
		FileStore.putString(file, text);
	}
	
	public void downLoadHtml(File root,WebElement ele)
	{
		root.mkdirs();
		HtmlCollect collect = new HtmlCollect();
		String content = collect.getContent(ele);
		String fileName = System.currentTimeMillis()+".txt";
		File file  = new File(root,fileName);
		FileStore.putString(file, content);
	}
}
