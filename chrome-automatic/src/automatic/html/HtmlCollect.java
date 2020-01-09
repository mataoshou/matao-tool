package automatic.html;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HtmlCollect
{
	public String getText(WebElement ele)
	{
		String text = ele.getText();
		
		return text;
	}
	
	
	public List<String> getImgSrc(List<WebElement> eles)
	{
		List<String> result = new ArrayList();
		for(WebElement ele : eles)
		{
			if(ele.getTagName().equals("img"))
			{
				String src = ele.getAttribute("src").trim();
				if(src!=null&&src.length()>0)
				{
					result.add(src);
				}
			}
			else {
				result.addAll(getImgSrc(ele.findElements(By.tagName("img"))));
			}
		}
		return result;
		
	}
	
	public List<String> getVideoSrc(List<WebElement> eles)
	{
		List<String> result = new ArrayList();
		for(WebElement ele : eles)
		{
			if(ele.getTagName().equals("video"))
			{
				String src = ele.getAttribute("src").trim();
				if(src!=null&&src.length()>0)
				{
					result.add(src);
				}
			}
			else {
				result.addAll(getVideoSrc(ele.findElements(By.tagName("img"))));
			}
		}
		return result;
	}
	
	public String getContent(WebElement ele)
	{
		String html = ele.getAttribute("innerHTML");
		return html;
	}
}
