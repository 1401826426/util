package util.clazz.classUtil;

public class ClassTests6 {
	
	public static void main(String[] args) {
		A a = new A() ;
		a.test(1, 1); 
	}
	
	@SuppressWarnings("unused")
	private static class A{
		
		public void test(Integer a,Integer b){
			System.out.println("testInteger");
		}; 
		
		
		public void test(Integer a,String s){
			System.out.println("testIntegerString");
		}
		
		public void test(String a,Integer b){
			System.out.println("testStringInteger");
		}
		
		public void test(String s1,String s2){
			System.out.println("testString");
		}; 
		
	}
	
}
