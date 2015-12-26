package cn.com.maxtech.autocoder.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import cn.com.maxtech.autocoder.bean.ClassEntity;
import cn.com.maxtech.autocoder.bean.PropertyEntity;

public class PdmUtil {
	//表名转类名规则
	private static final String TABLE_TO_POJO = "_";
	
	private static List<ClassEntity> tableList;
	
	private static File pdmFile;
	
	private static File tempFile;

//	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//		File file = new File("D:\\affix\\zczw.pdm");
//		analyze(file);
//
//	}
	
	public static void analyze() throws Exception{
		if(pdmFile==null||!pdmFile.exists()||!pdmFile.isFile()){
			System.out.println("文件格式错误或不存在");
			return;
		}
		if(tableList!=null&&pdmFile.equals(tempFile)){
			System.out.println("文件已解析");
			return;
		}
		//结果集合
		List<ClassEntity> result = new ArrayList<ClassEntity>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(pdmFile);
		Element root = document.getRootElement();
		Iterator<Element> els = root.elementIterator("RootObject");
		while(els.hasNext()){
			Element RootObject = els.next();
			Element Children= RootObject.element("Children");
			Element Model = Children.element("Model");
			Element Tables = Model.element("Tables");
			Iterator<Element> tables = Tables.elementIterator("Table");
			while(tables.hasNext()){
				Element table = tables.next();
				//获取字段
				Element Columns = table.element("Columns");
				if(Columns==null){//该表没有字段
					continue;
				}
				Iterator<Element> columns = Columns.elementIterator("Column"); 
				List<PropertyEntity> properties = new ArrayList<PropertyEntity>();
				while(columns.hasNext()){
					Element column = columns.next();
					//创建属性并添加
					PropertyEntity property = new PropertyEntity();
					property.setType(getPropertyTypeName(getTagText(column,"DataType")));
					property.setName(getTagText(column,"Code"));
					property.setNote(getTagText(column,"Name"));
					property.setColumnCode(property.getName().toUpperCase());
					properties.add(property);
				}
				//新建类对象
				ClassEntity classEntiry = new ClassEntity();
				classEntiry.setTableCode(getTagText(table,"Code").toUpperCase());
				classEntiry.setTableName(getTagText(table,"Name"));
//				classEntiry.setPackageName("");
				classEntiry.setTableComment(getTagText(table,"Comment"));
				classEntiry.setProperties(properties);
				result.add(classEntiry);
				System.out.println("表<"+getTagText(table,"Name")+">自动编码已完成");
			}
		}
		tempFile = pdmFile;
		tableList = result;
	}

	public static List<ClassEntity> getTableList() {
		return tableList;
	}

	public static File getPdmFile() {
		return pdmFile;
	}

	public static void setPdmFile(File pdmFile) {
		PdmUtil.pdmFile = pdmFile;
	}
	
	public static void init(){
		tableList = null;
		pdmFile = null;
	}

	public static void listChildren(Element parent){
		if("Tables".equals(parent.getName())){
			System.out.println("标签名称为："+parent.getName());
//			//内容
//			Iterator<Element> els = parent.elementIterator();
//			while(els.hasNext()){
//				Element e = els.next();
//				if("Name".equals(e.getName())){
//					System.out.println("内容为："+e.getTextTrim());
//				}
//			}
			System.out.println(parent.getParent().getNamespacePrefix()+":"+parent.getParent().getName());
			System.out.println(parent.getParent().getParent().getNamespacePrefix()+":"+parent.getParent().getParent().getName());
			System.out.println(parent.getParent().getParent().getParent().getNamespacePrefix()+":"+parent.getParent().getParent().getParent().getName());
			System.out.println(parent.getParent().getParent().getParent().getParent().getNamespacePrefix()+":"+parent.getParent().getParent().getParent().getParent().getName());
		}else{
			//子元素
			Iterator<Element> els = parent.elementIterator();
			while(els.hasNext()){
				Element e = els.next();
				//System.out.println(e.getName());
				listChildren(e);
			}
		}
	}
	/**
	 * 获取标签间内容
	 * @param element
	 * @param tagName
	 * @return
	 * @throws Exception
	 */
	public static String getTagText(Element element,String tagName) throws Exception{
		Element e = element.element(tagName);
		return e==null?"":e.getTextTrim();
	}
	/**
	 * 根据表名称获得类名
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static String tableNameToClassName(String tableName) throws Exception{
		return tableName;
	}
	/**
	 * 根据
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static String getPropertyTypeName(String propertyType) throws Exception{
		if(propertyType.contains("bigint")){
			return "Long";
		}else if(propertyType.contains("^binary(\\d)$")){
			return "String";
		}else if(propertyType.contains("bit")){
			return "String";
		}else if(propertyType.contains("bit varying")){
			return "String";
		}else if(propertyType.contains("char")){
			return "String";
		}else if(propertyType.contains("character")){
			return "char";
		}else if(propertyType.contains("character varying")){
			return "String";
		}else if(propertyType.contains("date")){
			return "Date";
		}else if(propertyType.contains("datetime")){
			return "Date";
		}else if(propertyType.contains("dec")){
			return "String";
		}else if(propertyType.contains("decimal")){
			return "BigDecimal";
		}else if(propertyType.contains("double")){
			return "Double";
		}else if(propertyType.contains("double precision")){
			return "Double";
		}else if(propertyType.contains("float")){
			return "Float";
		}else if(propertyType.contains("image")){
			return "String";
		}else if(propertyType.contains("int")){
			return "Integer";
		}else if(propertyType.contains("integer")){
			return "Integer";
		}else if(propertyType.contains("long binary")){
			return "Long";
		}else if(propertyType.contains("long bit varying")){
			return "Long";
		}else if(propertyType.contains("long varbit")){
			return "Long";
		}else if(propertyType.contains("long varchar")){
			return "Long";
		}else if(propertyType.contains("money")){
			return "String";
		}else if(propertyType.contains("numeric")){
			return "String";
		}else if(propertyType.contains("smalldatetime")){
			return "String";
		}else if(propertyType.contains("smallint")){
			return "String";
		}else if(propertyType.contains("smallmoney")){
			return "String";
		}else if(propertyType.contains("time")){
			return "Date";
		}else if(propertyType.contains("timestamp")){
			return "Date";
		}else if(propertyType.contains("tinyint")){
			return "Integer";
		}else if(propertyType.contains("uniqueidentifier")){
			return "String";
		}else if(propertyType.contains("uniqueidentifierstr")){
			return "String";
		}else if(propertyType.contains("unsigned bigint")){
			return "String";
		}else if(propertyType.contains("unsigned int")){
			return "String";
		}else if(propertyType.contains("unsigned integer")){
			return "String";
		}else if(propertyType.contains("unsigned smallint")){
			return "String";
		}else if(propertyType.contains("unsigned tinyint")){
			return "String";
		}else if(propertyType.contains("varbinary")){
			return "String";
		}else if(propertyType.contains("varbit")){
			return "String";
		}else if(propertyType.contains("varchar")){
			return "String";
		}else if(propertyType.contains("xml")){
			return "String";
		}else{
			return "String";
		}
	}
}
