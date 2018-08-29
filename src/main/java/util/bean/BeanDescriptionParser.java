package util.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class BeanDescriptionParser {
	
	private static BeanDescriptionParser instance = new BeanDescriptionParser() ; 
	
	private WeakHashMap<Class<?>,BeanDescription> map = new WeakHashMap<>() ; 
	
	
	public static BeanDescriptionParser getInstance(){
		return instance ; 
	}
	
	/**
	 * 这里解析的时候如果clazz上有泛型属性是不处理的
	 * @param clazz
	 * @return
	 */
	public BeanDescription parse(Class<?> clazz){
		if(clazz == null){
			return null ; 
		}
		BeanDescription beanDescription = map.get(clazz) ; 
		if(beanDescription != null){
			return beanDescription  ; 
		}
		Map<String,PropertyDescription> pdMap = PropertyDescriptionParser.getInstance().parse(clazz) ; 
		List<PropertyDescription> list = new ArrayList<>() ; 
		list.addAll(pdMap.values()) ; 
		beanDescription = new BeanDescription() ; 
		beanDescription.setClazz(clazz);
		beanDescription.setPds(list);
		return beanDescription ;  
	}

	
}








