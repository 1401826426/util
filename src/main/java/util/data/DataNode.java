package util.data;

import java.lang.reflect.Type;

public interface DataNode {
	
	public String getName() ; 
	
	public Object resolve(Type type) ;

	
}
