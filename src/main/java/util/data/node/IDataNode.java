package util.data.node;

import java.lang.reflect.Type;

public interface IDataNode {
	
	public String getName() ; 
	
	public Object resolve(Type type) ;

	public String toString(int blank) ; 
	
	public void addNode(IDataNode dataNode); 
	
}
