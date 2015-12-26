package cn.com.maxtech.autocoder.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.maxtech.autocoder.util.ConsoleUtil;

@RestController
@RequestMapping(value="/sys")
public class ConsoleController {

	@RequestMapping(value="/console",method=RequestMethod.GET)
	public List<String> getConsole(HttpServletRequest request,HttpServletResponse response){
		return ConsoleUtil.getMsg();
	}
	
	@RequestMapping(value="/console/clear",method=RequestMethod.GET)
	public String getConsoleClear(HttpServletRequest request,HttpServletResponse response){
		ConsoleUtil.clearQueue();
		return "ok";
	}
}
