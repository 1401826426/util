package util.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import util.bean.BeanDescription;
import util.bean.BeanDescriptionParser;
import util.bean.PropertyDescription;
import util.clazz.ClazzUtil;
import util.data.node.IDataNode;
import util.data.node.ListNode;
import util.data.node.ObjectNode;
import util.data.node.ValueNode;

public class DataEncoder {
	
	public static DataEncoder instance = new DataEncoder() ; 
	
	public IDataNode encode(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		 return doEncode("", obj) ; 
	}
	
	public IDataNode doEncode(String name,Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz = obj.getClass() ;
		if(ClazzUtil.isSimpleClass(clazz) || clazz == String.class){
			return new ValueNode(name,String.valueOf(obj)) ; 
		}
		IDataNode node = null ; 
		if(ClazzUtil.isCollectClass(clazz) || ClazzUtil.isArray(clazz)){
			node = new ListNode(name) ; 
		}else{
			node = new ObjectNode(name) ; 
		}
		BeanDescription beanDesciption = BeanDescriptionParser.getInstance().parse(clazz) ; 
		for(PropertyDescription pd:beanDesciption.getPds()){
			Method readMethod = pd.getReadMethod() ; 
			readMethod.setAccessible(true) ;
			Object fieldObj = readMethod.invoke(obj) ; 
			node.addNode(doEncode(pd.getName(),fieldObj));
		}
		return node ;
	}
	
}
