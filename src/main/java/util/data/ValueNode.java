package util.data;

import java.lang.reflect.Type;

import util.clazz.ClazzUtil;

public class ValueNode extends AbstractNode{
	
	private String value ; 
	
	public ValueNode(String name, String value) {
		super(name) ; 
		this.value = value ; 
	}

	public Object resolve(Type type){
		if(type instanceof Class<?>){
			Class<?> clazz = (Class<?>)type ; 
			return ClazzUtil.strToSimpleObject(clazz, value) ; 
		}
		return null ; 
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("") ; 
		sb.append(value) ; 
		return sb.toString() ; 
	}
	
	
	
}
