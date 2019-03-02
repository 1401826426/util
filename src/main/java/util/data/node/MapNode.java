package util.data.node;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapNode extends AbstractNode{
	
	public MapNode(String name) {
		super(name);
	}

	private Map<IDataNode,IDataNode> map ; 

	@Override
	public Object resolve(Type type) {
		if(type instanceof Map){
			ParameterizedType parameterizedType = (ParameterizedType)type ;
			Class<?> clazz = (Class<?>)parameterizedType.getRawType()  ;
			Type keyType = parameterizedType.getActualTypeArguments()[0] ; 
			Type valueType = parameterizedType.getActualTypeArguments()[1] ; 
//			Class<?> mapClass = (Class<?>)type ;
			try {
				Map<Object,Object> result = getMapClass(clazz) ;
				for(Map.Entry<IDataNode,IDataNode> entry:map.entrySet()){
					Object key = entry.getKey().resolve(keyType) ;
					Object value = entry.getValue().resolve(valueType) ; 
					result.put(key, value) ; 
				}
				return result ; 
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} 
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getMapClass(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		if("Map".equals(clazz.getSimpleName())){
			return new HashMap<>() ; 
		}
		return (Map<Object, Object>) clazz.newInstance();
	}

	@Override
	public String toString(int blank) {
//		StringBuilder sb = new StringBuilder(getBlank(blank)) ;
//		if(name != null && !"".equals(name)){
//			sb.append("\""+name+"\""+":") ; 
//		}
//		sb.append("{\n") ; 
//		List<IDataNode> list = new ArrayList<>(nodes.values()) ; 
//		for(int i = 0;i < list.size();i++){
//			IDataNode node = list.get(i) ; 
//			sb.append(node.toString(blank+1)) ;
//			if(i != list.size()-1){
//				sb.append(",") ; 
//			}
//			sb.append("\n") ; 
//		}
//		sb.append(getBlank(blank)+"}") ; 
//		return sb.toString();
		return "" ; 
	}

	@Override
	public void addNode(IDataNode dataNode) {
		
	}

}
