package util.clazz;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

public class JavaBeanTests {
	
	public static void main(String[] args) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(A.class) ;
		System.err.println(beanInfo);
		
		System.err.println("======================================================");
		BeanInfo beanInfoB = Introspector.getBeanInfo(B.class) ; 
		for(PropertyDescriptor pd:beanInfoB.getPropertyDescriptors()){
			Class<?> clazz = pd.getPropertyType() ;
			Class<?> pec = pd.getPropertyEditorClass() ;
			System.err.println("======="+pec) ; 
			System.err.println("========"+clazz) ;
			TypeVariable<?>[] tvs = clazz.getTypeParameters() ;
			if(tvs != null){
				for(TypeVariable<?> tv:tvs){
					System.err.println(tv.getTypeName());
				}
			}
		}
	}
	
}

class A{
	A a ; 
    B b ; 
	int c ;
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	} 
	
}

class B{
	int intB ; 
	String strB ; 
	List<Integer> listB ; 
	Map<String,Integer> mapB ;
	public int getIntB() {
		return intB;
	}
	public void setIntB(int intB) {
		this.intB = intB;
	}
	public String getStrB() {
		return strB;
	}
	public void setStrB(String strB) {
		this.strB = strB;
	}
	public List<Integer> getListB() {
		return listB;
	}
	public void setListB(List<Integer> listB) {
		this.listB = listB;
	}
	public Map<String, Integer> getMapB() {
		return mapB;
	}
	public void setMapB(Map<String, Integer> mapB) {
		this.mapB = mapB;
	} 
	
}