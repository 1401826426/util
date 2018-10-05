package util.clazz.classUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClassTest7{
	
	public static void main(String[] args){ 
		Object obj = Proxy.newProxyInstance(ClassTest7.class.getClassLoader(),new Class<?>[]{A.class}, new B()) ;
		System.out.println(obj);
	}
	
	private static class B implements InvocationHandler{

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return method.invoke(proxy, args) ; 
		}
		
	}
	
	private static interface A{
		void test() ; 
	}
}
