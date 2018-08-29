package util.data;

import java.io.InputStream;

public class DataParser {
	
	private NodeParser nodeParser ; 
	
	public DataParser(NodeParser nodeParser){
		this.nodeParser = nodeParser ; 
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parse(Class<T> clazz,InputStream is){
		DataNode node = nodeParser.parse(is) ; 
		System.err.println(node);
		return (T)node.resolve(clazz) ; 
	}
	
}




