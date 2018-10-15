package util.data.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
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

	public ObjectNode(String name) {
		super(name) ;
		this.nodes = new HashMap<String,DataNode>() ; 
	}

	public void addDataNode(String name,DataNode dataNode){
		this.nodes.put(name, dataNode)  ; 
	}

	public void setNodes(Map<String, DataNode> nodes) {
		this.nodes = nodes;
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
					if(node != null){
						Object val = node.resolve(pd.getGenericType()) ;
						if(val != null){						
							pd.getWriteMethod().invoke(obj, val) ; 
						}
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
		StringBuilder sb = new StringBuilder("{") ;
		for(Map.Entry<String,DataNode> entry:nodes.entrySet()){
			sb.append(entry.getKey()+":"+entry.getValue()+",") ; 
		}
		sb.append("}") ; 
		return sb.toString();
	}

	@Override
	public String toString(int blank) {
		StringBuilder sb = new StringBuilder(getBlank(blank)) ;
		if(name != null && !"".equals(name)){
			sb.append(name+":") ; 
		}
		sb.append("{\n") ; 
		for(DataNode node:nodes.values()){
			sb.append(node.toString(blank+1)+",\n") ; 
		}
		sb.append(getBlank(blank)+"}") ; 
		return sb.toString();
	}

	@Override
	public void addNode(DataNode dataNode) {
		this.nodes.put(dataNode.getName(), dataNode) ; 
	}
	
	

}
