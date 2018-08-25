package util.clazz.classUtil;

import java.lang.reflect.Method;

import util.clazz.ClazzUtil;

public class ClassUtilTests2 {
	
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Class<?> clazz = E.class ; 
		Method method = clazz.getMethod("a") ; 
		System.err.println(ClazzUtil.getDecalareInterface(method));
		System.err.println("==================================");
		clazz = F.class ; 
		method = clazz.getMethod("a") ;
		System.err.println(ClazzUtil.getDecalareInterface(method));
		System.err.println("========="
				+ "=========================");
		clazz = A.class ; 
		method = clazz.getMethod("a") ;
		System.err.println(ClazzUtil.getDecalareInterface(method));
		System.err.println("==================================");
		clazz = B.class ; 
		method = clazz.getMethod("a") ;
		System.err.println(ClazzUtil.getDecalareInterface(method));
		System.err.println("==================================");
		clazz = D.class ; 
		method = clazz.getMethod("a") ;
		System.err.println(ClazzUtil.getDecalareInterface(method));
	}
	
}

interface A{
	void a() ; 
}

interface B extends A{
	
}

interface C extends A{
	
}

interface D extends B,C{
	
}

class E implements D{

	@Override
	public void a() {}
	
}

class F{
	public void a(){}
}
