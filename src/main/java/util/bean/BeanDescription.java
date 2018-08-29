package util.bean;

import java.util.List;

public class BeanDescription {
	
	private Class<?> clazz ; 
	
	private List<PropertyDescription> pds ;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<PropertyDescription> getPds() {
		return pds;
	}

	public void setPds(List<PropertyDescription> pds) {
		this.pds = pds;
	} 
	
	
	
}
