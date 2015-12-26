package cn.com.maxtech.autocoder.util;

import java.io.File;  
import org.apache.tools.ant.Project;  
import org.apache.tools.ant.taskdefs.Zip;  
import org.apache.tools.ant.types.FileSet;

public class ZipUtil {
      
    /**
     * 执行压缩操作
     * @param srcPathName 需要被压缩的文件/文件夹
     * @param pathName 最终压缩生成的压缩文件：目录+压缩文件名.zip
     */
    public static void compressExe(String srcPathName,String destFileName) {  
        File srcdir = new File(srcPathName);
        File zipFile = new File(destFileName);
        if (!srcdir.exists()){
            throw new RuntimeException("源文件:"+srcPathName + "不存在！");  
        }
        if (zipFile.exists()){
            System.out.println("目标文件已存在，删除后继续");
            zipFile.delete();
        }
          
        Project prj = new Project();  
        Zip zip = new Zip();  
        zip.setProject(prj);  
        zip.setDestFile(zipFile);  
        FileSet fileSet = new FileSet();  
        fileSet.setProject(prj);  
        fileSet.setDir(srcdir);  
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");  
        //fileSet.setExcludes(...); //排除哪些文件或文件夹  
        zip.addFileset(fileSet);  
        zip.execute();  
    } 
}