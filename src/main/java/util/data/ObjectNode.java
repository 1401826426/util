package util.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import util.bean.BeanDescription;
import util.bean.BeanDescriptionParser;
import util.bean.PropertyDescription;

public class ObjectNode extends AbstractNode{

	private Map<String,DataNode> nodes ; 

	public ObjectNode(String name,Map<String,DataNode> nodes) {
		super(name);
		this.nodes = nodes ; 
	}

	@Override
	public Object resolve(Type type) {
		if(type instanceof Class<?>){
			Class<?> clazz = (Class<?>)type ;
			try {
				Object obj = clazz.newInstance() ;
				BeanDescription bp = BeanDescriptionParser.getInstance().parse(clazz) ; 
				List<PropertyDescription> pds = bp.getPds() ; 
				for(PropertyDescription pd:pds){
					DataNode node = nodes.get(pd.getName()) ; 
					Object val = node.resolve(pd.getGenericType()) ;
					if(val != null){						
						pd.getWriteMethod().invoke(obj, val) ; 
					}
				}
				return obj;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			} 
			
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\n") ;
		for(Map.Entry<String,DataNode> entry:nodes.entrySet()){
			sb.append(entry.getKey()+":"+entry.getValue()+",\n") ; 
		}
		sb.append("}\n") ; 
		return sb.toString();
	}
	
	

}
