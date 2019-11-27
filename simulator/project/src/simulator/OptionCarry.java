package simulator;

import java.util.List;

public class OptionCarry
{
	
	PressUtils utils = new PressUtils();
	
	public void optionCarry(OptionItem option)
	{
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
			
		case STEP_TYPE_SINGLEKEYPRESS:
//			utils.moveTo(item.x, item.y);
			utils.keyPress(item.value[0]);
			break;
			
		case STEP_TYPE_LEFTDOUBLECLICK:
			utils.mouseDoubleClick(item.x, item.y);
			break;
			
		case STEP_TYPE_COMBINATIONKEYPRESS:
			utils.keyCombinationPress(item.value);;
			break;
			
		case STEP_TYPE_SHOWDESKTOP:
			utils.showDesktop();
			break;

		default:
			break;
		}
		
		
		
	}
	
	
	private final String STEP_TYPE_BEGIN = "begin";
	private final String STEP_TYPE_EDN = "end";
	
	private final String STEP_TYPE_SHOWDESKTOP = "showDesktop";
	private final String STEP_TYPE_LEFTCLICK = "leftClick";
	private final String STEP_TYPE_LEFTDOUBLECLICK = "leftDoubleClick";
	private final String STEP_TYPE_RIGHTCLICK = "rightClick";
	private final String STEP_TYPE_SINGLEKEYPRESS = "singleKeyPress";
	private final String STEP_TYPE_COMBINATIONKEYPRESS = "combinationKeyPress";
	
	
	
	
	
	
	
	
	
}
