package util.data.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.bean.BeanDescription;
import util.bean.BeanDescriptionParser;
import util.bean.PropertyDescription;

public class ObjectNode extends AbstractNode{

	private Map<String,IDataNode> nodes ; 

	public ObjectNode(String name,Map<String,IDataNode> nodes) {
		super(name);
		this.nodes = nodes ; 
	}

	public ObjectNode(String name) {
		super(name) ;
		this.nodes = new LinkedHashMap<String,IDataNode>() ; 
	}

	public void addDataNode(String name,IDataNode dataNode){
		this.nodes.put(name, dataNode)  ; 
	}

	public void setNodes(Map<String, IDataNode> nodes) {
		this.nodes = nodes;
	}

	public ValueNode getValueNode(String name){
		return (ValueNode)nodes.get(name) ; 
	}
	
	public ObjectNode getObjectNode(String name){
		return (ObjectNode) this.nodes.get(name) ; 
	}
	
	public ListNode getListNode(String name){
		return (ListNode)this.nodes.get(name) ; 
	}
	
	public IDataNode getNode(String name){
		return this.nodes.get(name) ; 
	}
	
	@Override
	public Object resolve(Type type) {
		if(type instanceof Class<?>){
			Class<?> clazz = (Class<?>)type ;
			try {
				clazz.getConstructor().setAccessible(true);
				Object obj = clazz.newInstance() ;
				BeanDescription bp = BeanDescriptionParser.getInstance().parse(clazz) ; 
				List<PropertyDescription> pds = bp.getPds() ; 
				for(PropertyDescription pd:pds){
					IDataNode node = nodes.get(pd.getName()) ;
					if(node != null){
						Object val = node.resolve(pd.getGenericType()) ;
						if(val != null){
							pd.getWriteMethod().setAccessible(true);
							pd.getWriteMethod().invoke(obj, val) ; 
						}
					}
				}
				return obj;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | NoSuchMethodException e) {
				e.printStackTrace();
			} 
			
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("") ;
		if(name != null && !"".equals(name)){
			sb.append("\""+name+"\""+":") ; 
		}
		sb.append("{") ; 
		List<IDataNode> list = new ArrayList<>(nodes.values()) ; 
		for(int i = 0;i < list.size();i++){
			IDataNode node = list.get(i) ; 
			sb.append(node.toString()) ;
			if(i != list.size()-1){
				sb.append(",") ; 
			}
		}
		sb.append("}") ;
		return sb.toString();
	}

	@Override
	public String toString(int blank) {
		StringBuilder sb = new StringBuilder(getBlank(blank)) ;
		if(name != null && !"".equals(name)){
			sb.append("\""+name+"\""+":") ; 
		}
		sb.append("{\n") ; 
		List<IDataNode> list = new ArrayList<>(nodes.values()) ; 
		for(int i = 0;i < list.size();i++){
			IDataNode node = list.get(i) ; 
			sb.append(node.toString(blank+1)) ;
			if(i != list.size()-1){
				sb.append(",") ; 
			}
			sb.append("\n") ; 
		}
		sb.append(getBlank(blank)+"}") ; 
		return sb.toString();
	}

	@Override
	public void addNode(IDataNode dataNode) {
		this.nodes.put(dataNode.getName(), dataNode) ; 
	}
	
	

}
