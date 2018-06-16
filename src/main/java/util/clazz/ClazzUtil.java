package util.clazz;

import java.lang.reflect.Method;

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

}














