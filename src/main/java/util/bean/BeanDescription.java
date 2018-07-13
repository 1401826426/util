package util.bean;

import java.util.List;

import util.str.StringUtils;

public class BeanDescription {
	
	private Class<?> myClazz ; 
	
	private List<ColumnDescription> columns ;
	
	private List<Class<?>> interfaces ; 
	
	private Class<?> extendClass ; 
	
	private Package myPackage ;
	
	private String selfCode ; 

	public Class<?> getClazz() {
		return myClazz;
	}

	public void setClazz(Class<?> clazz) {
		this.myClazz = clazz;
	}

	public List<ColumnDescription> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDescription> columns) {
		this.columns = columns;
	}

	public List<Class<?>> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Class<?>> interfaces) {
		this.interfaces = interfaces;
	}

	public Class<?> getExtendClass() {
		return extendClass;
	}

	public void setExtendClass(Class<?> extendClass) {
		this.extendClass = extendClass;
	}

	public Package getMyPackage() {
		return myPackage;
	}

	public void setMyPackage(Package myPackage) {
		this.myPackage = myPackage;
	}

	public String getSelfCode() {
		return selfCode;
	}

	public void setSelfCode(String selfCode) {
		this.selfCode = selfCode;
	}

	public String getBeanClassString(){
		StringBuilder sb = new StringBuilder("") ; 
		sb.append("package "+myPackage.getName()+"\n") ; 
		sb.append("\n") ;
		if(extendClass != null){
			Package classPackage = extendClass.getPackage() ;
			if(!myPackage.getName().equals(classPackage.getName())){
				sb.append("import "+extendClass.getCanonicalName()) ; 
			}
		}
		if(interfaces != null){
			for(Class<?> clazz:interfaces){
				Package classPackage = clazz.getPackage() ;
				if(!myPackage.getName().equals(classPackage.getName())){
					sb.append("import "+clazz.getCanonicalName()) ; 
				}
			}
		}
		sb.append("\n") ; 
		sb.append("public class "+myClazz.getName()) ;
		if(extendClass != null){
			sb.append(" extend "+extendClass.getName()) ; 
		}
		if(interfaces != null && interfaces.size() > 0){
			sb.append(" implements ") ; 
			boolean first = true; 
			for(Class<?> clazz:interfaces){
				if(!first){
					sb.append(", ") ; 
				}
				sb.append(clazz.getName()) ; 
			}
		}
		sb.append("{\n") ; 
		if(columns != null){
			for(ColumnDescription description:columns){
				if(description.getComment() != null){					
					sb.append("\t"+"//" + description.getComment()+"\n") ; 
				}
				sb.append("\t" + "private "+ description.getType().getTypeName() + " " + description.getName()+"\n"); 
				sb.append("\n") ; 
			}
			for(ColumnDescription description:columns){
				sb.append("\t" + "public "+ description.getType().getTypeName() + " get"+StringUtils.firstCharUpper(description.getName())+"(){\n") ;
				sb.append("\t" + "return " + description.getName()+"\n");
				sb.append("\t" +"}\n") ; 
				sb.append("\n") ; 
				sb.append("\t" + "public void "+""
						+ " set"+StringUtils.firstCharUpper(description.getName())+"("+description.getType().getTypeName() + " "
								+ description.getName() +"){\n") ;
				sb.append("\t" + "this." + description.getName()+"="+description.getName() + "\n");
				sb.append("\t" +"}\n") ;
			}
		}
		sb.append("\n") ; 
		sb.append(selfCode + "\n") ; 
		sb.append("\n") ; 
		sb.append("}\n") ; 
		return sb.toString() ; 
	}
	
	
		
}












