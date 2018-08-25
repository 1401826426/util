package util.bean;

import java.util.List;

public interface DataNode {
	
	DataNode getValue(String name) ; 
	
	List<DataNode> getListValue(String name) ;  
	
}
