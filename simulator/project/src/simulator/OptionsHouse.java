package simulator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import simulator.pojo.OptionItem;
import simulator.pojo.StepItem;
import simulator.util.DomUtil;

public class OptionsHouse
{
	List<OptionItem> options = new ArrayList();
	
	public void load(File config) throws IOException, DocumentException
	{
		
		System.out.println("配置文件路径：" + config.getPath());
		
		System.out.println("加载要执行的操作");
		Document doc = DomUtil.getDocument(config);
		
	 	Element rootEle = doc.getRootElement();
	 	
	 	List<Element> eles = rootEle.elements("option");
	 	
	 	for(Element ele :eles)
	 	{
	 		OptionItem option = new OptionItem();
	 		option.name = DomUtil.getValue(ele, "name", "option" + (options.size()+1));
	 		option.count = Integer.valueOf(DomUtil.getValue(ele, "count", "1"));
	 		option.maxCount = Integer.valueOf(DomUtil.getValue(ele, "maxCount", "1"));
	 		
	 		List<Element> stepEles = ele.elements("step");
	 		List<StepItem> steps = new ArrayList();
	 		for(Element stepEle : stepEles)
	 		{
	 			StepItem step = new StepItem();
	 			step.name = DomUtil.getValue(ele, "name", option.name +".step" + (steps.size()+1));
	 			step.type = DomUtil.getValue(stepEle, "type", "ignore");
	 			String postion = DomUtil.getValue(stepEle, "postion", "0,0");
	 			getPostion(step,postion);
	 			
	 			
 				step.content =DomUtil.child(stepEle, "content", "abcdefg");
	 			
	 			String value = DomUtil.getValue(stepEle, "value", "0");
	 			step.value = value;
	 			steps.add(step);
	 		}
	 		option.steps = steps;
	 		System.out.println("添加步骤数量：" + option.steps.size());
	 		options.add(option);
	 		
	 	}
	 	
	 	System.out.println("加载完成!");
	 	
	 	
	}
	
	
	public void getPostion(StepItem step,String postion)
	{
		String[] strs = duplicate(postion.split(","));
		if(strs.length!=2)
		{
			System.out.println(postion +"位置数据不合法");
		}
		step.x = Integer.valueOf(strs[0]);
		step.y = Integer.valueOf(strs[1]);
	}
	
	public String[] duplicate(String[] strs)
	{
		List<String> list = Arrays.asList(strs);
		
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)==null||list.get(i).trim().length()==0)
			{
				list.remove(i);
				i--;
			}
		}
		strs = new String[list.size()];
		for(int i=0;i<list.size();i++)
		{
			strs[i] = list.get(i);
		}
		return strs;
	}
}
