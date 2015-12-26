package cn.com.maxtech.autocoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(value="propConfig")
@ConfigurationProperties(prefix="application.prop")
public class PropConfig {

	private String basePath;
	private String baseDir;
	private String buildPath;
	private String fltPath;
	
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getBaseDir() {
		return baseDir;
	}
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	public String getBuildPath() {
		return buildPath;
	}
	public void setBuildPath(String buildPath) {
		this.buildPath = buildPath;
	}
	public String getFltPath() {
		return fltPath;
	}
	public void setFltPath(String fltPath) {
		this.fltPath = fltPath;
	}
	
}
