package parse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DivideHtml
{
	List<TextContent> line = new ArrayList();
	String contentText = "";

	// 提取文本内容
	public void getConentLine(Node root)
	{
		// 若是文本节点的话，直接返回
		if (root.getNodeType() == Node.TEXT_NODE)
		{
			TextContent content = new TextContent();
			NamedNodeMap e = root.getAttributes();
			content.content = root.getNodeValue().trim();
			if (content.content.length() > 0)
			{
				line.add(content);
				contentText += content;
			}
			return;
		}
		if (root.getNodeType() == Node.ELEMENT_NODE)
		{
			Element elmt = (Element) root;
			// 抛弃脚本
			if (elmt.getTagName().equals("STYLE")
					|| elmt.getTagName().equals("SCRIPT")
					||elmt.getTagName().equals("LI")
					||elmt.getTagName().equals("A"))
			{
				return;
			}
			String style = elmt.getAttribute("style");
			if (style != null && style.indexOf("display:none") >= 0)
			{
				return;
			}

			NodeList children = elmt.getChildNodes();
			StringBuilder text = new StringBuilder();
			for (int i = 0; i < children.getLength(); i++)
			{
				getConentLine(children.item(i));
			}
		}
		// 对其它类型的节点，返回空值
		// line.add("");
	}

	public void parser()
	{
		HtmlText text = new HtmlText();
		int max = 0;
		List<Integer>  t_size = new ArrayList();
		
		int index = 0;
		// 获取最长文本长度
		for (int i = 0; i < line.size(); i++)
		{
			TextContent t = line.get(i);
			if (t.content.length() != 0)
			{
				t_size.add(t.content.length());
				if (t.content.length() > max)
				{
					max = t.content.length();
					index = i;
				}
			}
		}
		
		t_size.remove(index);
		
		
		// 添加十份间隔
		int[] intervals = new int[10];
		int inter = max / 10;
		if (inter < 5)
			return;

		for (int i = 0; i < intervals.length; i++)
		{
			intervals[i] = inter * i;
		}
		// 根据字数评分
		for (int i = 0; i < line.size(); i++)
		{
			TextContent t = line.get(i);
			for (int j = 0; j < intervals.length; j++)
			{
				if (t.content.length() > intervals[j])
				{
					t.score += 10;
				}
			}
			System.out.println("第一次评分：" + t.score + " 内容" + t.content);
		}
		// 根据周围的文本，进行评分
		for (int i = 0; i < line.size(); i++)
		{
			TextContent t = line.get(i);
			if (i > 0)
			{
				TextContent p = line.get(i - 1);
				if (p.score > 60)
				{
					t.score += 5;
				}
				if (Math.abs(p.content.length() - t.content.length()) < inter)
				{
					t.score += 5;
				}
				if (t.score - p.score > 20)
				{
					t.score += 20;
				}
				if (t.content.length() - p.content.length() > inter)
				{
					t.score += 10;
				}
				System.out.println("第二次评分：" + t.score + " 内容" + t.content);
			}
			//根据相邻的长度和分数差进行评分
			if (i < line.size() - 1)
			{
				TextContent n = line.get(i + 1);
				if (n.score > 60)
				{
					t.score += 5;
				}
				if (Math.abs(n.content.length() - t.content.length()) < inter)
				{
					t.score += 5;
				}
				if (t.score - n.score > 20)
				{
					t.score += 20;
				}
				if (t.content.length() - n.content.length() > inter)
				{
					t.score += 10;
				}
				System.out.println("第三次评分：" + t.score + " 内容" + t.content);
			}
		}
	}

	public void init(String content) throws SAXException, IOException
	{
		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new StringReader(content)));
		Document doc = parser.getDocument();
		Node body = doc.getElementsByTagName("BODY").item(0);
		body.getTextContent();
		getConentLine(body);
		System.out.println(contentText);
		parser();
		int begin = -1;
		int end = -1;
		int errCode = 0;
		for (int i = 0; i < line.size(); i++)
		{
			if (errCode > 3)
				break;
			TextContent t = line.get(i);
			System.out.println("评分：" + t.score + "  内容： " + t.content);
			if (t.score >= 60)
			{
				errCode = 0;
				if (begin == -1)
					begin = i;
				end = i;
			} else
			{
				if (begin != -1 && t.score < 30)
				{
					errCode++;
				}
			}
		}
		System.out.println("----------------------------------------");
		if (begin == -1)
		{
			System.out.println("没有符合要求的内容");
			return;
		}
		for (int i = begin; i <= end; i++)
		{
			System.out.println(line.get(i).content);

		}
	}

	public static String getString(File dstFile) throws Exception
	{
		InputStream inStream = new FileInputStream(dstFile);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try
		{
			byte[] buf = new byte[4096];
			while (true)
			{
				int n = inStream.read(buf);
				if (n <= 0)
					break;
				out.write(buf, 0, n);
			}
		} finally
		{
			inStream.close();
		}
		return out.toString("UTF-8");
	}

	public static void main(String[] args)
	{
		DivideHtml html = new DivideHtml();
		File f = new File("c:/111.html");
		String content = "";
		try
		{
			content = getString(f);
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
		try
		{
			html.init(content);
		} catch (SAXException | IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
