package cn.com.maxtech.autocoder.web.model;

public class TableModel {
	
	private String tableCodes;
	private String packageName;
	private String packagePath;
	private String rule;
	private String tablePrefix;
	private Integer idstrategy;
	public String getTableCodes() {
		return tableCodes;
	}
	public void setTableCodes(String tableCodes) {
		this.tableCodes = tableCodes;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getTablePrefix() {
		return tablePrefix;
	}
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	public Integer getIdstrategy() {
		return idstrategy;
	}
	public void setIdstrategy(Integer idstrategy) {
		this.idstrategy = idstrategy;
	}

}
