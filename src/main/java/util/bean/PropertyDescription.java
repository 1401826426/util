package util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PropertyDescription {
	
	private Method writeMethod ; 
	
	private Method readMethod ; 
	
	private Field property ;
	
	public PropertyDescription(Method writeMethod, Method readMethod, Field property) {
		super();
		this.writeMethod = writeMethod;
		this.readMethod = readMethod;
		this.property = property;
	}

	public Method getWriteMethod() {
		return writeMethod;
	}

	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}

	public Method getReadMethod() {
		return readMethod;
	}

	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	public Field getProperty() {
		return property;
	}

	public void setProperty(Field property) {
		this.property = property;
	}
	
	public ParameterizedType getParameterizedType(){
		Type type = property.getGenericType() ; 
		if(type instanceof ParameterizedType){
			return (ParameterizedType)type ; 
		}
		return null ; 
	}
	
	public Class<?> getType(){
		return property.getType() ; 
	}

	public String getName() {
		return property.getName() ; 
	}
	
	//如果这个属性的类型带有泛型,那么返回的就是泛型的第一个参数类型
	//如果类型为空,则直接返回数组类型的Component类型
	//没有则返回空
	public Type getComponetType() {
		if(property.getType().isArray()){
			Type type = property.getGenericType() ; 
			if(type instanceof GenericArrayType){
				GenericArrayType genericArrayType = (GenericArrayType)type ; 
				return genericArrayType.getGenericComponentType();
			}
		}
		ParameterizedType parameterizedType = getParameterizedType() ; 
		if(parameterizedType == null){
			return null ; 
		}
		Type[] types = parameterizedType.getActualTypeArguments() ;
		if(types != null && types.length > 0){ 
			return types[0] ; 
		}
		return null;
	}

	public Type getGenericType() {
		return property.getGenericType() ; 
	}
	
}








