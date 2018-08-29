package util.clazz.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class A {
	
	int a ;
	B b ;
	List<Integer> c ;
	List<List<List<Integer>>> d ; 
	List<Integer>[] e ;
	Set<List<Integer>> f; 
	
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	public List<Integer> getC() {
		return c;
	}
	public void setC(List<Integer> c) {
		this.c = c;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public List<List<List<Integer>>> getD() {
		return d;
	}
	public void setD(List<List<List<Integer>>> d) {
		this.d = d;
	}
	public List<Integer>[] getE() {
		return e;
	}
	public void setE(List<Integer>[] e) {
		this.e = e;
	}
	public Set<List<Integer>> getF() {
		return f;
	}
	public void setF(Set<List<Integer>> f) {
		this.f = f;
	}
	@Override
	public String toString() {
		return "A [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + Arrays.toString(e) + ", f=" + f + "]";
	}
	
	
}
