package util.data;

import java.io.File;
import java.io.InputStream;

import util.data.node.DataNode;

public interface NodeParser {
	
	public DataNode parse(InputStream is); 
	
	public DataNode parse(File file) ; 
	
	public DataNode parse(String s) ; 

}
