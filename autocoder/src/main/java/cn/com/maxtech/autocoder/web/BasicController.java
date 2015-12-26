package cn.com.maxtech.autocoder.web;

public class BasicController {
	
	public static final String getSuccessMessage(String msg){
		return "{\"status\":\"1\",\"msg\":\""+msg+"\"}";   
	}
	
	public static final String getFailureMessage(String msg){
		return "{\"status\":\"0\",\"msg\":\""+msg+"\"}";   
	}

}
