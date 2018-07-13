package util.bean;

import java.lang.reflect.Type;

public class ColumnDescription {
	
	private String name ;
	
	private Type type ; 
	
	private String comment ;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	//TODO
	public String getJdbcTypeString(){
		return "" ; 
	}
	
}
