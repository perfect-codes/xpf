package cn.com.maxtech.autocoder.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"id","packageName","className","note","properties"})
public class ClassEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String tableName;
	private String tableCode;
	private String packageName;
	private String className;
	private String note;
	private String tableComment;
	private List<PropertyEntity> properties;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public List<PropertyEntity> getProperties() {
		return properties;
	}
	public void setProperties(List<PropertyEntity> properties) {
		this.properties = properties;
	}

}
