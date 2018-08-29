package util.data;

import java.io.File;
import java.io.InputStream;

public interface NodeParser {
	
	public DataNode parse(InputStream is); 
	
	public DataNode parse(File file) ; 
	
}
