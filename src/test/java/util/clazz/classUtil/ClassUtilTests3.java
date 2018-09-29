package util.clazz.classUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressWarnings({"unused","rawtypes"})
public class ClassUtilTests3 {
	
	
	public static void main(String[] args){
		
		Object a = new ArrayList<A>() ;
		
		A<Integer> newA = new A<>() ; 
		newA.es[0] = 1 ; 
		
	}
	
	
	@SuppressWarnings("hiding")
	private static class A<E>{
		E[] es ;

		@SuppressWarnings("unchecked")
		public A() {
			es = (E[])new Object[10] ; 
		} 
		
		
	}
	
}
