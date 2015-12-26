package cn.com.maxtech.autocoder.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.maxtech.autocoder.bean.ClassEntity;
import cn.com.maxtech.autocoder.config.PropConfig;
import cn.com.maxtech.autocoder.util.ConsoleUtil;
import cn.com.maxtech.autocoder.util.PdmUtil;
import cn.com.maxtech.autocoder.util.TemplateUtil;
import cn.com.maxtech.autocoder.util.ZipUtil;
import cn.com.maxtech.autocoder.web.model.TableModel;

@RestController
@RequestMapping(value="/sys")
public class TableController extends BasicController{
	
	@Autowired
	private PropConfig propConfig;

	/**
	 * 
	* @Title: getTables 
	* @Description: 获取所有设计的表
	* @param @param request
	* @param @param response
	* @return Map<String,Object>返回类型 
	 */
	@RequestMapping(value="/tables",method=RequestMethod.POST)
	public Map<String,Object> getTables(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			List<ClassEntity> beans = PdmUtil.getTableList();
			if(beans!=null){
				result.put("rows",beans);
				result.put("total",beans.size());
			}
			ConsoleUtil.info("数据列表获取成功");
		} catch (Exception e) {
			ConsoleUtil.error("数据列表获取失败");
		}
		return result;
	}
	/**
	 * 
	* @Title: analyzePdm 
	* @Description: 解析PDM文件
	* @param @param request
	* @param @param response
	* @return String    返回类型 
	 */
	@RequestMapping(value="/analyze",method=RequestMethod.GET)
	public String analyzePdm(HttpServletRequest request,HttpServletResponse response){
		try {
			PdmUtil.analyze();
			ConsoleUtil.info("PDM文件解析成功");
		} catch (Exception e) {
			System.out.println("error:文件名获取失败");
			ConsoleUtil.error("PDM文件解析失败");
		}
		return "ok";
	}
	/**
	 * 
	* @Title: buildClass 
	* @Description: 构建源码并压缩
	* @param @param model
	* @param @param request
	* @param @param response
	* @return String    返回类型 
	 */
	@RequestMapping(value="/build",method=RequestMethod.POST)
	public String buildClass(@ModelAttribute TableModel model,HttpServletRequest request,HttpServletResponse response){
		String folderName = new Date().getTime()+"";
		List<ClassEntity> beans = PdmUtil.getTableList();
		if(model.getTableCodes()!=null&&!"".equals(model.getTableCodes().trim())){
			String [] tables = model.getTableCodes().trim().split(",");
			for(int i=0;i<tables.length;i++){
				String code = tables[i].trim();
				if(code.length()>0){
					for(int j=0;j<beans.size();j++){
						if(code.equals(beans.get(j).getTableCode())){
							try {
								beans.get(j).setPackageName(model.getPackageName());
								beans.get(j).setClassName(TemplateUtil.getClassName(beans.get(j).getTableCode(),model.getTablePrefix()));
								//beans.get(j).setTableName(tableName);
//								System.out.println(beans.get(j).getTableCode());
								File buildFolder = new File(propConfig.getBuildPath());
						        if(!buildFolder.exists()){
						        	buildFolder.mkdirs();
						        }
								TemplateUtil.createFiles(beans.get(j),propConfig.getBuildPath()+"/"+folderName,propConfig.getFltPath(),model.getIdstrategy());
								ConsoleUtil.info(beans.get(j).getTableName()+"构建成功");
								break;
							} catch (Exception e) {
								e.printStackTrace();
								ConsoleUtil.error(beans.get(j).getTableName()+"构建失败");
								return getFailureMessage("构建失败");
							}
						}
					}
				}
			}
			ZipUtil.compressExe(propConfig.getBuildPath()+"/"+folderName, propConfig.getBuildPath()+"/"+folderName+".zip");
		}
		return getSuccessMessage(folderName);
	}
	/**
	 * 
	* @Title: zipDownload 
	* @Description: 源码包下载
	* @param @param request
	* @param @param response
	* @return String 
	 */
	@RequestMapping(value="/zipdownload/{filename}",method=RequestMethod.GET)
	public String zipDownload(@PathVariable String filename,HttpServletRequest request,HttpServletResponse response){

		if(filename==null||"".equals(filename.trim())||"构建失败".equals(filename.trim())){
			return getFailureMessage("源码包不存在,或者已被移除");
		}
		String filePath = propConfig.getBuildPath()+"/"+filename+".zip";
		try {
			response.addHeader("Content-Disposition", "attachment;filename="+filename+".zip");
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			BufferedInputStream bin = new BufferedInputStream(new FileInputStream(filePath));
			byte[] bf = new byte[1024];
			int length = 0;
			int total = 0;
			while((length=bin.read(bf))>0){
				out.write(bf,0,length);
				total += length;
			}
			response.setContentLength(total);
			out.flush();
			out.close();
			bin.close();
		} catch (Exception e) {
			e.printStackTrace();
			ConsoleUtil.error("源码包下载失败");
			return getFailureMessage("下载失败");
		}
//		PdmUtil.init();
		ConsoleUtil.info("源码包下载成功");
		return getSuccessMessage("下载成功");
	}

}
