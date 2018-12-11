package util.clazz;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sun.javafx.collections.MappingChange.Map;

//1.ParameterizedType： 表示一种参数化的类型，比如Collection，即普通的泛型。 
//2.TypeVariable：是各种类型变量的公共父接口，就是泛型里面的类似T、E。 
//3.GenericArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型，比如List<>[]，T[]这种。 
//4.WildcardType：代表一种通配符类型表达式，类似? super T这样的通配符表达式。

public class ClazzUtil {
	
	private static Set<Class<?>> SIMPLE_CLASS_SET ; 
	
	static{
		SIMPLE_CLASS_SET = new HashSet<>() ; 
		SIMPLE_CLASS_SET.add(boolean.class) ;
		SIMPLE_CLASS_SET.add(byte.class) ;
		SIMPLE_CLASS_SET.add(short.class) ;
		SIMPLE_CLASS_SET.add(char.class) ;
		SIMPLE_CLASS_SET.add(int.class) ;
		SIMPLE_CLASS_SET.add(long.class) ;
		SIMPLE_CLASS_SET.add(float.class) ;
		SIMPLE_CLASS_SET.add(double.class) ;
		
		SIMPLE_CLASS_SET.add(Boolean.class) ;
		SIMPLE_CLASS_SET.add(Byte.class) ;
		SIMPLE_CLASS_SET.add(Short.class) ;
		SIMPLE_CLASS_SET.add(Character.class) ;
		SIMPLE_CLASS_SET.add(Integer.class) ;
		SIMPLE_CLASS_SET.add(Long.class) ;
		SIMPLE_CLASS_SET.add(Float.class) ;
		SIMPLE_CLASS_SET.add(Double.class) ;
	}
	
	public static void doWithMethods(Class<?> clazz , MethodCallBack cb){
		doWithMethods(clazz, cb,null);
	}
	
	public static void doWithMethods(Class<?> clazz, MethodCallBack cb,MethodFilter mf){
		Method[] methods = clazz.getDeclaredMethods() ;  
		for(Method method:methods){
			if(mf != null && !mf.matches(method)){
				continue ; 
			}
			try {
				cb.doWith(method) ;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(clazz.getSuperclass() != null){
			doWithMethods(clazz.getSuperclass(), cb, mf);
		}else if(clazz.isInterface()){			
			Class<?>[] clazzes = clazz.getInterfaces() ;
			for(Class<?> ca :clazzes){
				doWithMethods(ca, cb, mf);
			}
		}
	}


	public interface MethodCallBack{

		void doWith(Method method) throws IllegalAccessException;
		
	} 
	
	public interface MethodFilter{
		boolean matches(Method method);
	}
	
	
//	public Type getComponenetType(Type type){
////		if(type instanceof ParameterizedType){
////			
////		}
//		return null ; 
//	}
//
	/**
	 * 找对应方法的申明的类或接口
	 * @param method
	 * @return
	 */
	public static Class<?> getDecalareInterface(Method method){
		Class<?> clazz = method.getDeclaringClass() ; 
		Set<Class<?>> vis = new HashSet<Class<?>>() ;
		if(clazz.isInterface()){
			return detect(clazz,vis,method) ; 
		}else{
			Class<?>[] interfaces = clazz.getInterfaces() ;
			if(interfaces != null){
				for(Class<?> cl:interfaces){
					Class<?> cla = detect(cl,vis,method) ; 
					if(cla != null){
						return cla ; 
					}
				}
			}
		}
		return null;
	}
	
	private static Class<?> detect(Class<?> cl, Set<Class<?>> vis,Method method) {
		if(vis.contains(cl)){
			return null ; 
		}
		vis.add(cl) ; 
		try{
			Method detectMethod = cl.getDeclaredMethod(method.getName(),method.getParameterTypes()) ; 
			if(detectMethod != null){
				return cl ; 
			}
		}catch(Exception e){
			
		}
		Class<?>[] interfaces = cl.getInterfaces() ;
		if(interfaces != null){
			for(Class<?> cla:interfaces){
				Class<?> detect = detect(cla, vis, method) ;
				if(detect != null){
					return detect ; 
				}
			}
		}
		return null;
	}
	
	public static <T extends Annotation> T getAnnotation(Class<?> cc,Class<T> anno){
		while(cc != null && cc != Object.class){
			T t = cc.getAnnotation(anno) ;
			if(t != null){
				return t ; 
			}
			cc = cc.getSuperclass() ; 
		}
		return null ; 
	}

	public static <T extends Annotation> Class<?> getAnnotationClass(Class<?> cc , Class<T> anno){
		return detectAnnoClass(cc,anno,new HashSet<Class<?>>()) ; 
	}
	
	private static <T extends Annotation> Class<?> detectAnnoClass(Class<?> cc, Class<T> anno, Set<Class<?>> vis) {
		if(cc == null){
			return null ; 
		}
		if(vis.contains(cc)){
			return null ; 
		}
		vis.add(cc) ; 
		if(cc == Object.class){
			return null ; 
		}
		T t = cc.getAnnotation(anno) ;
		if(t != null){
			return cc ; 
		}
		Class<?> result = detectAnnoClass(cc.getSuperclass(), anno,vis) ;
		if(result != null){
			return result ; 
		}
		Class<?>[] interfaces = cc.getInterfaces() ;
		for(Class<?> inter:interfaces){
			result = detectAnnoClass(inter, anno,vis) ;
			if(result != null){
				return result ; 
			}
		}
		return null;
	}

	public static boolean isSimpleClass(Class<?> clazz){
		return SIMPLE_CLASS_SET.contains(clazz) ; 
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T strToSimpleObject(Class<T> clazz,String value){
		if(value == null){
			return null ; 
		}
		if(clazz == Boolean.class || clazz == boolean.class){
			return (T)Boolean.valueOf(value) ; 
		}else if(clazz == Byte.class || clazz == byte.class){
			return (T)Byte.valueOf(value) ; 
		}else if(clazz == Short.class || clazz == short.class){
			return (T)Short.valueOf(value) ; 
		}else if(clazz == Character.class || clazz == char.class){
			if(value.length() > 0){				
				return (T)((Character)value.charAt(0)) ;  
			}
		}else if(clazz == Integer.class || clazz == int.class){
			return (T)Integer.valueOf(value) ;  
		}else if(clazz == Long.class || clazz == long.class){
			return (T)Long.valueOf(value) ; 
		}else if(clazz == Float.class || clazz == float.class){
			return (T)Float.valueOf(value) ; 
		}else if(clazz == Double.class || clazz == double.class){
			return (T)Double.valueOf(value) ; 
		}else if(clazz == String.class){
			return (T)value ; 
		}
		return null ;
	}
	
	public static boolean isCollectClass(Class<?> clazz) {
		if(clazz != null && clazz.isAssignableFrom(Collection.class)){
			return true ; 
		}
		return false;
	}
	
	
	public static boolean isBoolean(Class<?> clazz){
		return clazz == Boolean.class || clazz == boolean.class ; 
	}
	

	public static boolean isArray(Class<?> pdClass) {
		return pdClass == Array.class;
	}

	public static boolean isMapClass(Class<?> rawClass) {
		return rawClass == Map.class;
	}

	public static Class<?> getClazzFromType(Type type) {
		if(type instanceof Class<?>){
			return (Class<?>)type ; 
		}else if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType)type ; 
			return (Class<?>)parameterizedType.getRawType() ;  
		}else if(type instanceof GenericArrayType){
			return Array.class ; 
		}
		return null;
	}

	

	
}














