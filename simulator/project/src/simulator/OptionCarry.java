package simulator;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import simulator.pojo.OptionItem;
import simulator.pojo.StepItem;
import simulator.util.KeyCodeTable;
import simulator.util.PressUtils;

public class OptionCarry
{
	
	PressUtils utils = new PressUtils();
	
	public void optionCarry(OptionItem option)
	{
//		boolean locked=java.awt.Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
//		
//		if(locked)
//		{
//			utils.keyPress(KeyEvent.VK_CAPS_LOCK);
//		}
		
		System.out.println("开始执行操作：" + option.name);
		
		List<StepItem> steps = option.steps;
		
		for(int i =0 ;i<option.count;i++)
		{
			for(StepItem item : option.steps)
			{
				stepCarry(item);
			}
		}
		System.out.println("操作完成：" + option.name);
	}
	
	public void stepCarry(StepItem item)
	{
		System.out.println("执行步骤"+ item.name);
		switch (item.type)
		{
		case STEP_TYPE_BEGIN:
//			utils.moveTo(item.x, item.y);
			break;
			
		case STEP_TYPE_EDN:
//			utils.moveTo(item.x, item.y);
			break;
		case STEP_TYPE_LEFTCLICK:
			utils.mouseLeftClick(item.x, item.y);
			break;
		case STEP_TYPE_RIGHTCLICK:
			utils.mouseRightClick(item.x, item.y);
			break;
			
		case STEP_TYPE_KEYPRESS:
//			utils.moveTo(item.x, item.y);
			utils.keyPress(KeyCodeTable.one().getCode(item.value));
			break;
			
		case STEP_TYPE_LEFTDOUBLECLICK:
			utils.mouseDoubleClick(item.x, item.y);
			break;
			
		case STEP_TYPE_COMBINATIONKEYPRESS:
			utils.keyPress(KeyCodeTable.one().getCode(item.value.split(",")));;
			break;
			
		case STEP_TYPE_SHOWDESKTOP:
			utils.showDesktop();
			break;
			
		case STEP_TYPE_WAIT:
			utils.waitOption(Integer.valueOf(item.value));
			break;
			
			
		case STEP_TYPE_TEXTKEYPRESS:
			for(int i=0;i<item.value.length();i++)
			{
				utils.keyPress(KeyCodeTable.single.getCode(String.valueOf(item.value.charAt(i))));
			}
			break;
			
		case STEP_TYPE_TEXT:
			setClipbordContents(String.valueOf(item.value));
			utils.keyPress(KeyEvent.VK_CONTROL,KeyEvent.VK_V);
			break;
			
		case STEP_TYPE_RANDOMTEXT:
			int count = item.content.length();
			Random random = new Random();
			int length = Integer.valueOf(item.value);
			for(int i=0;i<length;i++)
			{
				int index = random.nextInt(count);
				setClipbordContents(String.valueOf(item.content.charAt(index)));
				utils.keyPress(KeyEvent.VK_CONTROL,KeyEvent.VK_V);
			}
			break;

		default:
			break;
		}
		
		
		
	}
	
	private void setClipbordContents(String contents) {
		StringSelection stringSelection = new StringSelection( contents );
		// 系统剪贴板
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	
	private final String STEP_TYPE_BEGIN = "begin";
	private final String STEP_TYPE_EDN = "end";
	
	private final String STEP_TYPE_TEXT = "text";
	private final String STEP_TYPE_TEXTKEYPRESS = "textKeyPress";
	
	private final String STEP_TYPE_RANDOMTEXT = "randomtext";
	private final String STEP_TYPE_SHOWDESKTOP = "showDesktop";
	private final String STEP_TYPE_LEFTCLICK = "leftClick";
	private final String STEP_TYPE_LEFTDOUBLECLICK = "leftDoubleClick";
	private final String STEP_TYPE_RIGHTCLICK = "rightClick";
	private final String STEP_TYPE_KEYPRESS = "keyPress";
	private final String STEP_TYPE_COMBINATIONKEYPRESS = "combinationKeyPress";
	
	private final String STEP_TYPE_WAIT = "wait";
	
	
	
	
	
	
	
}
