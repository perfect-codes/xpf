package cn.com.maxtech.autocoder.util;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUtil {
	
	private static List<String> queue = new ArrayList<String>();
	
	public static void error(String msg){
		StringBuffer html = new StringBuffer();
		html.append("<span class='fontred'>").append(msg).append("</span><br/>");
		queue.add(html.toString());
	}
	
	public static void info(String msg){
		StringBuffer html = new StringBuffer();
		html.append("<span class='fontgreen'>").append(msg).append("</span><br/>");
		queue.add(html.toString());
	}
	
	public static void clearQueue(){
		queue.clear();
	}
	
	public static List<String> getMsg(){
		return queue;
	}

}
