package util.bean;

import java.util.List;
import java.util.Map;

public class BeanDescription {
	
	private Class<?> clazz ; 
	
	private List<PropertyDescription> pds ;
	
	private Map<String,PropertyDescription> namePdsMap ;

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

	public Map<String, PropertyDescription> getNamePdsMap() {
		return namePdsMap;
	}

	public void setNamePdsMap(Map<String, PropertyDescription> namePdsMap) {
		this.namePdsMap = namePdsMap;
	} 
	
	
	
}
