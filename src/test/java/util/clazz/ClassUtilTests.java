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
//		TypeVariable<?>[] typeVars = mapReturn.getTypeParameters();
//		for(TypeVariable<?> type:typeVars){
//			System.err.println(type.getName());
//			System.err.println(type.getGenericDeclaration());
////			System.err.println(type.getGenericDeclaration().);
//		}
//		Method listMethod = clazz.getMethod("list") ;
		
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
}
