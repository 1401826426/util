package util.clazz.bean;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import util.bean.BeanDescription;
import util.bean.BeanDescriptionParser;
import util.bean.PropertyDescription;

public class BeanDescriptionTest {
	
	public static void main(String[] args) {
		BeanDescription beanDescription = BeanDescriptionParser.getInstance().parse(A.class) ;
		for(PropertyDescription pd:beanDescription.getPds()){
			Field field = pd.getProperty(); 
			Type type = field.getGenericType();
			System.err.println("=============================================================================================");
			System.err.println(type);
			System.err.println(pd.getProperty()) ; 
//			System.err.println(pd.getReadMethod());
//			System.err.println(pd.getWriteMethod());
			if(type instanceof ParameterizedType){
				ParameterizedType parameterizedType = (ParameterizedType)type ; 
				Type rawType = parameterizedType.getRawType() ;
				if(rawType instanceof Class<?>){
					Class<?> clazz = (Class<?>)rawType ;
					BeanDescription bd = BeanDescriptionParser.getInstance().parse(clazz) ;
					List<PropertyDescription> pdds = bd.getPds()  ;
					for(PropertyDescription pdd:pdds){
						System.err.println("================-=========================="+pdd.getGenericType());
					}
				}
			}
			Type componetType = pd.getComponetType() ;
			System.err.println("=================="+componetType);
			if(componetType instanceof ParameterizedType){
				ParameterizedType parameterizedType = (ParameterizedType)componetType ;
				Type[] types = parameterizedType.getActualTypeArguments() ; 
				for(Type t:types){
					System.err.println("==============================="+t);
				}
			}
			
		}
	}

}




