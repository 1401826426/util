package util.data.node;

import java.lang.reflect.Type;

public interface DataNode {
	
	public String getName() ; 
	
	public Object resolve(Type type) ;

	public String toString(int blank) ; 
	
	public void addNode(DataNode dataNode); 
	
}
