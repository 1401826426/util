package util.clazz;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map; 

public class ClassUtilTests {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException{
		Class<?> clazz = A.class ; 
		Method mapMethod = clazz.getMethod("map") ;
		Type type = mapMethod.getGenericReturnType() ; 
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType)type; 
			Type[] types = parameterizedType.getActualTypeArguments() ;
			for(Type t:types){
				if(t instanceof Class<?>){
					System.err.println(((Class<?>)t).getName());
				}
			}
		}
		System.err.println("=================================================================");
		Method method = D.class.getMethod("map") ; 
		System.err.println(method.getDeclaringClass().getName());
//		System.err.println(method.get);
	}
	
	
	private static interface A{
		public Map<B,C> map(); 
		
		public List<B> list() ; 
		
		public C[] array() ; 
 		
		public B generic() ; 
	}
	
	private static class B{
		
	}
	
	private static class C{
		
	}
	
	private static class D implements A{

		@Override
		public Map<B, C> map() {
			return null;
		}

		@Override
		public List<B> list() {
			return null;
		}

		@Override
		public C[] array() {
			return null;
		}

		@Override
		public B generic() {
			return null;
		}
		
	}
}
