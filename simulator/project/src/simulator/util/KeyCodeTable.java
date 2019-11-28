package simulator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.glass.events.KeyEvent;

public class KeyCodeTable
{
	public static KeyCodeTable single =new KeyCodeTable();
	
	public static KeyCodeTable one()
	{
		return single;
	}
	
	private KeyCodeTable()
	{
		load();
	}
	
	public int[] getCode(String keyName)
	{
		if(keyName==null||keyName.length()==0) return null;
		
		if(keyName.length()==1)
		{
			return m_map.get(keyName);
		}
		
		return m_map.get(keyName.toLowerCase());
	}
	
	public int[] getCode(String[] keyNames)
	{
		if(keyNames==null||keyNames.length==0) return null;
		
		List<Integer> list = new ArrayList();
		
		for(String key : keyNames)
		{
			if(key==null||key.length()==0)continue;
			int[] codes =m_map.get(key);
			
			for(Integer code : codes)
			{
				list.add(code);
			}
			
		}
		
		int[] codes = new int[list.size()];
		
		for(int i=0;i<list.size();i++)
		{
			codes[i] = list.get(i).intValue();
			
		}
		
		return codes;
		
	}
	
	
	private Map<String,int[]> m_map = new HashMap();
	
	private void load()
	{
		m_map.put("backspace", new int[]{KeyEvent.VK_BACKSPACE});
		m_map.put("tab", new int[]{KeyEvent.VK_TAB});
		m_map.put("enter", new int[]{KeyEvent.VK_ENTER});
		m_map.put("shift", new int[]{KeyEvent.VK_SHIFT});
		m_map.put("ctrl", new int[]{KeyEvent.VK_CONTROL});
		m_map.put("alt", new int[]{KeyEvent.VK_ALT});
		m_map.put("pause", new int[]{KeyEvent.VK_PAUSE});
		m_map.put("break", new int[]{KeyEvent.VK_PAUSE});
		m_map.put("pause/break", new int[]{KeyEvent.VK_PAUSE});
		m_map.put("caps lock", new int[]{KeyEvent.VK_CAPS_LOCK});
		m_map.put("page up", new int[]{KeyEvent.VK_PAGE_UP});
		m_map.put("page down", new int[]{KeyEvent.VK_PAGE_DOWN});
		m_map.put("end", new int[]{KeyEvent.VK_END});
		m_map.put("insert", new int[]{KeyEvent.VK_INSERT});
		m_map.put("space", new int[]{KeyEvent.VK_SPACE});
		
		
		m_map.put("win", new int[]{KeyEvent.VK_WINDOWS});

		
		m_map.put("0", new int[]{KeyEvent.VK_0});
		m_map.put("1", new int[]{KeyEvent.VK_1});
		m_map.put("2", new int[]{KeyEvent.VK_2});
		m_map.put("3", new int[]{KeyEvent.VK_3});
		m_map.put("4", new int[]{KeyEvent.VK_4});
		m_map.put("5", new int[]{KeyEvent.VK_5});
		m_map.put("6", new int[]{KeyEvent.VK_6});
		m_map.put("7", new int[]{KeyEvent.VK_7});
		m_map.put("8", new int[]{KeyEvent.VK_8});
		m_map.put("9", new int[]{KeyEvent.VK_9});
		m_map.put("a", new int[]{KeyEvent.VK_A});
		m_map.put("b", new int[]{KeyEvent.VK_B});
		m_map.put("c", new int[]{KeyEvent.VK_C});
		m_map.put("d", new int[]{KeyEvent.VK_D});
		m_map.put("e", new int[]{KeyEvent.VK_E});
		m_map.put("f", new int[]{KeyEvent.VK_F});
		m_map.put("g", new int[]{KeyEvent.VK_G});
		m_map.put("h", new int[]{KeyEvent.VK_H});
		m_map.put("i", new int[]{KeyEvent.VK_I});
		m_map.put("j", new int[]{KeyEvent.VK_J});
		m_map.put("k", new int[]{KeyEvent.VK_K});
		m_map.put("l", new int[]{KeyEvent.VK_L});
		m_map.put("m", new int[]{KeyEvent.VK_M});
		m_map.put("n", new int[]{KeyEvent.VK_N});
		m_map.put("o", new int[]{KeyEvent.VK_O});
		m_map.put("p", new int[]{KeyEvent.VK_P});
		m_map.put("q", new int[]{KeyEvent.VK_Q});
		m_map.put("r", new int[]{KeyEvent.VK_R});
		m_map.put("s", new int[]{KeyEvent.VK_S});
		m_map.put("t", new int[]{KeyEvent.VK_T});
		m_map.put("u", new int[]{KeyEvent.VK_U});
		m_map.put("v", new int[]{KeyEvent.VK_V});
		m_map.put("w", new int[]{KeyEvent.VK_W});
		m_map.put("x", new int[]{KeyEvent.VK_X});
		m_map.put("y", new int[]{KeyEvent.VK_Y});
		m_map.put("z", new int[]{KeyEvent.VK_Z});
		
		
		m_map.put(")", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_0});
		m_map.put("!", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_1});
		m_map.put("@", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_2});
		m_map.put("#", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_3});
		m_map.put("$", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_4});
		m_map.put("%", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_5});
		m_map.put("^", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_6});
		m_map.put("&", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_7});
		m_map.put("*", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_8});
		m_map.put("(", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_9});
		m_map.put("A", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_A});
		m_map.put("B", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_B});
		m_map.put("C", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_C});
		m_map.put("D", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_D});
		m_map.put("E", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_E});
		m_map.put("F", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_F});
		m_map.put("G", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_G});
		m_map.put("H", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_H});
		m_map.put("I", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_I});
		m_map.put("J", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_J});
		m_map.put("K", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_K});
		m_map.put("L", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_L});
		m_map.put("M", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_M});
		m_map.put("N", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_N});
		m_map.put("O", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_O});
		m_map.put("P", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_P});
		m_map.put("Q", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_Q});
		m_map.put("R", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_R});
		m_map.put("S", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_S});
		m_map.put("T", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_T});
		m_map.put("U", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_U});
		m_map.put("V", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_V});
		m_map.put("W", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_W});
		m_map.put("X", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_X});
		m_map.put("Y", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_Y});
		m_map.put("Z", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_Z});
		
		
		m_map.put(";", new int[]{KeyEvent.VK_SEMICOLON});
		m_map.put("=", new int[]{KeyEvent.VK_EQUALS});
		m_map.put("-", new int[]{KeyEvent.VK_MINUS});
		m_map.put(".", new int[]{KeyEvent.VK_PERIOD});
		m_map.put("/", new int[]{KeyEvent.VK_SLASH});
//		m_map.put("`", new int[]{KeyEvent.VK_GREATER});
		m_map.put("[", new int[]{KeyEvent.VK_OPEN_BRACKET});
		m_map.put("\\", new int[]{KeyEvent.VK_BACK_SLASH});
		m_map.put("]", new int[]{KeyEvent.VK_CLOSE_BRACKET});
		
		m_map.put(":", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_SEMICOLON});
		m_map.put("+", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_EQUALS});
		m_map.put("_", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_MINUS});
		m_map.put(">", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_PERIOD});
		m_map.put("?", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_SLASH});
//		m_map.put("~", new int[]{16,192});
		m_map.put("{", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_OPEN_BRACKET});
		m_map.put("|", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_BACK_SLASH});
		m_map.put("}", new int[]{KeyEvent.VK_SHIFT,KeyEvent.VK_CLOSE_BRACKET});
		
		m_map.put("f1", new int[]{KeyEvent.VK_F1});
		m_map.put("f2", new int[]{KeyEvent.VK_F2});
		m_map.put("f3", new int[]{KeyEvent.VK_F3});
		m_map.put("f4", new int[]{KeyEvent.VK_F4});
		m_map.put("f5", new int[]{KeyEvent.VK_F5});
		m_map.put("f6", new int[]{KeyEvent.VK_F6});
		m_map.put("f7", new int[]{KeyEvent.VK_F7});
		m_map.put("f8", new int[]{KeyEvent.VK_F8});
		m_map.put("f9", new int[]{KeyEvent.VK_F9});
		m_map.put("f10", new int[]{KeyEvent.VK_F10});
		m_map.put("f11", new int[]{KeyEvent.VK_F11});
		m_map.put("f12", new int[]{KeyEvent.VK_F12});
		
		
	}
}
