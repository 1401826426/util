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
	 * 杩欓噷瑙ｆ瀽鐨勬椂鍊欏鏋渃lazz涓婃湁娉涘瀷灞炴�ф槸涓嶅鐞嗙殑
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
		beanDescription.setNamePdsMap(pdMap);
		return beanDescription ;  
	}

	
}








