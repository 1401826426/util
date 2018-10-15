package util.data;

import java.io.InputStream;

import util.data.node.DataNode;

public class DataParser {
	
	private NodeParser nodeParser ; 
	
	public DataParser(NodeParser nodeParser){
		this.nodeParser = nodeParser ; 
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parse(Class<T> clazz,InputStream is){
		DataNode node = nodeParser.parse(is) ; 
		System.out.println(node.toString(0));
		return (T)node.resolve(clazz) ; 
	}
	
}




