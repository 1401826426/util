package util.data;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import util.clazz.ClazzUtil;

public class ListNode extends AbstractNode{
	
	private List<DataNode> nodes  ; 
	
	public ListNode(String name,List<DataNode> nodes) {
		super(name);
		this.nodes = nodes ; 
	}

	
	@Override
	public Object resolve(Type type) {
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType)type ;
			Class<?> clazz = (Class<?>)parameterizedType.getRawType()  ;
			Collection<Object> collect = null;
			try {
				collect = getCollectObj(clazz);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} 
			Type t = parameterizedType.getActualTypeArguments()[0] ; 
			for(DataNode node:nodes){
				Object obj = node.resolve(t) ;
				collect.add(obj) ; 
			}
			return collect ; 
		}else if(type instanceof GenericArrayType){
			GenericArrayType genericArrayType = (GenericArrayType)type ; 
			Type compType = genericArrayType.getGenericComponentType() ; 
			Class<?> compClazz = ClazzUtil.getClazzFromType(compType) ; 
			Object[] arrays = (Object[])Array.newInstance(compClazz, nodes.size()) ;
			int pos = 0 ; 
			for(DataNode node:nodes){
				arrays[pos++] = node.resolve(compType) ; 
			}
			return arrays ; 
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Collection<Object> getCollectObj(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		if(clazz.isInterface()){
			if("List".equals(clazz.getSimpleName())){
				return new ArrayList<Object>() ; 
			}else if("Set".equals(clazz.getSimpleName())){
				return new HashSet<Object>() ; 
			}
		}
		return (Collection<Object>) clazz.newInstance() ; 
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[") ; 
		for(DataNode node:nodes){
			sb.append(node+",") ; 
		}
		sb.append("]") ;  
		return sb.toString() ; 
	}
	
	

}


