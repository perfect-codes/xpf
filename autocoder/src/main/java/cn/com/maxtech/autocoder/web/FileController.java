package cn.com.maxtech.autocoder.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.maxtech.autocoder.config.PropConfig;
import cn.com.maxtech.autocoder.util.ConsoleUtil;
import cn.com.maxtech.autocoder.util.PdmUtil;

@RestController
@RequestMapping(value="/sys")
public class FileController extends BasicController{
	
	@Autowired
	private PropConfig propConfig;
	
	@RequestMapping(value={"/fileUpload"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public String imageUpload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		  PdmUtil.init();
	      try {
	    	  
	    	  MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		      MultipartFile multipartFile = multipartRequest.getFile("file");
		      String contentType = multipartFile.getContentType().toLowerCase();
		      String objId = UUID.randomUUID().toString();
		      String fileName = multipartFile.getOriginalFilename();
		      String extendName = fileName.substring(fileName.lastIndexOf(".") + 1);
		      String fileExt = extendName.toLowerCase();
		      
		      File path = new File(propConfig.getBasePath());
		      if(!path.exists()){
		    	  path.mkdirs();
		      }
		      File file = new File(propConfig.getBasePath()+"/"+fileName);
		      if(file.exists()){
		    	  PdmUtil.setPdmFile(file);
		    	  return getFailureMessage("同名文件已存在");
		      }
		      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		      BufferedInputStream bis = new BufferedInputStream(multipartFile.getInputStream());
		      byte[] bf = new byte[1024];
		      int length = 0;
		      while((length=bis.read(bf))!=-1){
		    	  bos.write(bf,0,length);
		      }
		      bis.close();
		      bos.flush();
		      bos.close();
		      //上传成功
		      PdmUtil.setPdmFile(file);
		} catch (Exception e) {
			ConsoleUtil.error("PDM文件上传失败");
			return getFailureMessage("文件上传失败");
		}
	      ConsoleUtil.info("PDM文件上传成功");
	      return getSuccessMessage("文件上传成功");
	  }

}
