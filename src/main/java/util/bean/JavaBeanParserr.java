package util.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import util.clazz.ClazzUtil;

public class JavaBeanParserr {
	
	private IDataGetter dataGetter ; 
	
	public <T> Object parse(Class<T> clazz) throws IntrospectionException, InstantiationException, IllegalAccessException{
		T obj = clazz.newInstance() ; 
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors() ; 
		for(PropertyDescriptor pd:pds){
			if(ClazzUtil.isSimpleClass(pd.getClass())){
//				pd.getWriteMethod().invoke(obj,pd.)
			}
		}
 		return null ; 
	}
	
}
