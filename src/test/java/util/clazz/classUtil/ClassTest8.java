package util.clazz.classUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import util.clazz.ClazzUtil;

public class ClassTest8 {
	
	public static void main(String[] args){
		Class<?> clazz = TestDDD.class ; 
		System.out.println(ClazzUtil.getAnnotationClass(clazz, testAnno.class));
	}
	
	@testAnno
	private static interface TestA{
		
	}

	private static interface TestB extends TestA{
		
	}
	
	private static class TestCCC implements TestB,TestA{
		
	}
	
	private static class TestDDD extends TestCCC{
		
	}
	
	@Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	private static @interface testAnno{
		
	}
	
}

















