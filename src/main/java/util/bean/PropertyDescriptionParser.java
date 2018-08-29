package util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import util.clazz.ClazzUtil;
import util.str.StringUtils;

public class PropertyDescriptionParser {
	
	private static PropertyDescriptionParser instance = new PropertyDescriptionParser() ; 

	private String GETTER_PREFIX = "get" ; 
	
	private String SETTER_PREFIX = "set" ; 
	
	private String IS_PREFIX = "is" ; 
	
	public static PropertyDescriptionParser getInstance(){
		return instance ; 
	}

	public void parseOne(Class<?> clazz,Map<String,PropertyDescription> pdMap ) throws SecurityException{ 
		Field[] fields = clazz.getDeclaredFields() ;  
//		clazz.get
		for(Field field:fields){
			String name = field.getName() ; 
			if(pdMap.containsKey(name)){
				continue ; 
			}
			Method readMethod = null ; 
			Method writeMethod = null ;
			try{
				String readMethodName = null ; 
				if(ClazzUtil.isBoolean(field.getClass())){
					readMethodName = IS_PREFIX+StringUtils.firstCharUpper(name) ;
				}else{
					readMethodName = GETTER_PREFIX+StringUtils.firstCharUpper(name) ;
				}
				readMethod = clazz.getMethod(readMethodName) ;
				String writeMethodName = SETTER_PREFIX+StringUtils.firstCharUpper(name) ;
				writeMethod = clazz.getMethod(writeMethodName,new Class<?>[]{field.getType()}) ;
			}catch(NoSuchMethodException e){
				
			}
			if(readMethod != null && writeMethod != null){
				PropertyDescription pd = new PropertyDescription(writeMethod,readMethod,field) ; 
				pdMap.put(name, pd) ; 
			}
		}
	}
	
	
	public Map<String,PropertyDescription> parse(Class<?> clazz){
		Map<String,PropertyDescription> pdMap = new HashMap<String,PropertyDescription>() ; 
		while(clazz != null && clazz != Object.class){
			parseOne(clazz, pdMap) ;
			clazz = clazz.getSuperclass() ; 
		}
		return pdMap ; 
	}
	
}

























