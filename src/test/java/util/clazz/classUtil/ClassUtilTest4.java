package util.clazz.classUtil;

import java.util.Arrays;

public class ClassUtilTest4 {

	public static void main(String[] args) {

		A a = new B();
		a.datas[0] = 1;
		a.datas[1] = "2";
		B b = (B) a;
		b.datas[0] = 1;
		System.err.println(a);
	}

	private static class A {
		protected Object[] datas = new Object[10];

		@Override
		public String toString() {
			return "A [datas=" + Arrays.toString(datas) + "]";
		}

	}

	private static class B extends A {
		protected int[] datas = new int[10];

		@Override
		public String toString() {
			return "B [datas=" + Arrays.toString(datas) + "]";
		}

	}
	
	@SuppressWarnings("unused")
	private static class C<K>{
		private K[] k ;
		public C(){
//			this.k = new K[10] ; 
		}
	}

}
