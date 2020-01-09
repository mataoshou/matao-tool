package start;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import oper.GroupItem;
import oper.OperItem;
import oper.SimpleItem;
import oper.SourceItem;
import oper.TaskItem;
import util.DomUtil;

public class TaskHouse
{
	List<TaskItem> taskItems = new ArrayList();
	
	
	public void load(File config) throws IOException, DocumentException
	{
		System.out.println("配置文件路径：" + config.getPath());
		
		System.out.println("加载操作步骤");
		Document doc = DomUtil.getDocument(config);
		
	 	Element root = doc.getRootElement();
	 	
	 	
	 	List<Element> taskEles = root.elements("task");
	 	

	 	for(Element tele : taskEles)
	 	{
			String taskname = DomUtil.getValue(tele, "name", "task" + (taskItems.size()+1));
			System.out.println("开始添加任务：" +taskname);
	 		TaskItem item = new TaskItem();
	 		item.setInterval(Integer.valueOf(DomUtil.getValue(tele, "interval", "5")));
	 		item.setName(taskname);
	 		item.setSync(Integer.valueOf(DomUtil.getValue(tele, "sync", "1")));
	 		item.setShow(Integer.valueOf(DomUtil.getValue(tele, "show", "0")));
	 		
	 		List<Element> groupEles = tele.elements("group");
	 		
	 		for(Element gele :groupEles)
	 		{
	 			String groupname = DomUtil.getValue(gele, "name", "group" + (item.getList().size()+1));
	 			System.out.println("开始添加分组：" +groupname);
	 			GroupItem group = new GroupItem();
	 			group.setName(groupname);
	 			group.setInterval(Integer.valueOf(DomUtil.getValue(gele, "interval", "1")));
	 			group.setCount(Integer.valueOf(DomUtil.getValue(gele, "count", "1")));
	 			group.setShow(item.getShow());
	 			List<Element> sourceEles = gele.element("base").elements("source");
	 			System.out.println("开始添加数据源");
	 			for(Element sEle : sourceEles)
	 			{
	 				
	 				SourceItem sItem = new SourceItem();
	 				
	 				sItem.setSrc(DomUtil.getValue(sEle, "src", ""));
	 				
	 				String params = DomUtil.getValue(sEle, "params", "");
	 				sItem.setParams(params.split(","));
	 				sItem.setBegin(Integer.valueOf(DomUtil.getValue(sEle, "begin", "1")));
	 				sItem.setEnd(Integer.valueOf(DomUtil.getValue(sEle, "end", "1")));
	 				sItem.setInterval(Integer.valueOf(DomUtil.getValue(sEle, "interval", "1")));
	 				
	 				group.addSource(sItem);
	 			}
	 			System.out.println("完成添加数据源");
	 			
	 			System.out.println("开始添加操作");
	 			Element ops = gele.element("operation");
	 			group.setOperCount(Integer.valueOf(DomUtil.getValue(ops, "count", "1")));
	 			
	 			List<Element> oEles = gele.element("operation").elements("item");
	 			for(Element oEle : oEles)
	 			{
	 				OperItem oItem = new OperItem();
	 				
	 				oItem.setName("oper" + (group.getOpers().size()+1));
	 				oItem.setType(DomUtil.getValue(oEle, "type", ""));
	 				oItem.setValue(DomUtil.getValue(oEle, "value", ""));
	 				oItem.setBegin(Integer.valueOf(DomUtil.getValue(oEle, "begin", "1")));
	 				oItem.setEnd(Integer.valueOf(DomUtil.getValue(oEle, "end", "1")));
	 				oItem.setInterval(Integer.valueOf(DomUtil.getValue(oEle, "interval", "1")));
	 				List<Element> itemEles = oEle.elements("select");
	 				
	 				for(Element iEle :itemEles)
	 				{
	 					SimpleItem sItem = new SimpleItem();
	 					sItem.setKey(DomUtil.getValue(iEle, "type", ""));
	 					sItem.setValue(DomUtil.getValue(iEle, "value", ""));
	 					
	 					oItem.addItem(sItem);
	 				}
	 				
	 				group.addOpers(oItem);
	 			}
	 			System.out.println("完成添加操作");
	 			
	 			item.addGroup(group);
	 			System.out.println("完成添加分组：" +groupname);
	 		}
	 		taskItems.add(item);
	 		System.out.println("完成添加任务：" +taskname);
	 	}
	 	
	}
	
}
