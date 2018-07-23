package util.clazz;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

//1.ParameterizedType： 表示一种参数化的类型，比如Collection，即普通的泛型。 
//2.TypeVariable：是各种类型变量的公共父接口，就是泛型里面的类似T、E。 
//3.GenericArrayType：表示一种元素类型是参数化类型或者类型变量的数组类型，比如List<>[]，T[]这种。 
//4.WildcardType：代表一种通配符类型表达式，类似? super T这样的通配符表达式。

public class ClazzUtil {
	
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
	
	
	public Type getComponenetType(Type type){
//		if(type instanceof ParameterizedType){
//			
//		}
		return null ; 
	}

	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		
	}
	

	
}














