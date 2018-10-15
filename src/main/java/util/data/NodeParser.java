package util.data;

import java.io.File;
import java.io.InputStream;

import util.data.node.IDataNode;

public interface NodeParser {
	
	public IDataNode parse(InputStream is); 
	
	public IDataNode parse(File file) ; 
	
	public IDataNode parse(String s) ; 

}
