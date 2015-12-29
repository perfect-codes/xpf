package cn.com.maxtech.autocoder.util;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.com.maxtech.autocoder.bean.ClassEntity;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class TemplateUtil {
	
	/**
	 * 生成单个java文件
	 * @param version
	 * @param model
	 * @param className
	 * @param fltName
	 */
	public static void createFile(Configuration config,Version version,ClassEntity model,String className,String fltName,String createPath)throws Exception{
	    Template template = config.getTemplate(fltName);
		if(createPath!=null){
			File buildpath = new File(createPath);
			if(!buildpath.exists()){
				buildpath.mkdirs();
			}
		}
	    File file = new File(createPath+"/"+className);
	    FileWriter fw = null;
		fw = new FileWriter(file);
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("bean",model);
	    BeansWrapperBuilder builder = new BeansWrapperBuilder(version);
	    BeansWrapper w1 = builder.build();
	    template.process(map,fw,w1);
		fw.flush();
		fw.close();
	}
	
	public static void createFiles(ClassEntity model,String buildPath,String fltPath,Integer idstrategy)throws Exception{
		Version version = new Version(2,3,0);
        Configuration config = new Configuration(version);
    	File file = new File(fltPath);
    	if(!file.exists()){
    		file.mkdirs();
    	}
    	config.setDirectoryForTemplateLoading(file);
        //domain目录
    	String domainPath = buildPath+getPathFromPackageName(model.getPackageName())+"domain";
    	//repository目录
    	String repositoryPath = buildPath+getPathFromPackageName(model.getPackageName())+"repository";
    	//service目录
    	String servicePath = buildPath+getPathFromPackageName(model.getPackageName())+"service";
    	//controller目录
    	String controllerPath = buildPath+getPathFromPackageName(model.getPackageName())+"web";
    	//model目录
    	String modelPath = buildPath+getPathFromPackageName(model.getPackageName())+"web/model";
    	//page目录
    	String pageFolerName = model.getClassName().toLowerCase();
    	String pagePath = buildPath+"/"+pageFolerName;
        config.setEncoding(Locale.CHINA, "utf-8");
        if(idstrategy==0){
        	createFile(config,version,model,model.getClassName()+".java","domain_none.flt",domainPath);
        }else if(idstrategy==1){
        	createFile(config,version,model,model.getClassName()+".java","domain.flt",domainPath);
        }
		createFile(config,version,model,model.getClassName()+"Repository.java","repository.flt",repositoryPath);
		createFile(config,version,model,model.getClassName()+"Service.java","service.flt",servicePath);
		createFile(config,version,model,model.getClassName()+"ServiceImpl.java","service_impl.flt",servicePath);
		createFile(config,version,model,model.getClassName()+"Controller.java","controller.flt",controllerPath);
		createFile(config,version,model,model.getClassName()+"Model.java","model.flt",modelPath);
		//生成页面
		createFile(config,version,model,pageFolerName+"_list.html","list_page.flt",pagePath);
		createFile(config,version,model,pageFolerName+"_add.html","add_page.flt",pagePath);
	}
	
	public static String getClassName(String tableName,String tablePrefix)throws Exception{
		if(tableName==null){
			return "";
		}
		StringBuffer className = new StringBuffer("");
		String[] fragments = tableName.split("_");
		for(String fgm:fragments){
			if(!fgm.equalsIgnoreCase(tablePrefix)){
				className.append(letterFormat(fgm));
			}
		}
		return className.toString();
	}
	
	public static String letterFormat(String letter)throws Exception{
		String fc = letter.substring(0, 1).toUpperCase();
		return fc+letter.substring(1).toLowerCase();
	}
	
	public static String firstLowerCase(String str)throws Exception{
		String fc = str.substring(0, 1).toLowerCase();
		return fc+str.substring(1);
	}
	
	public static String getPathFromPackageName(String packageName)throws Exception{
		if(packageName!=null){
			packageName = packageName.replaceAll("\\.","\\/");
		}
		return "/"+packageName+"/";
	}

}
