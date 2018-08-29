package util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class AbstractDataNodeParser implements NodeParser{

	@Override
	public DataNode parse(File file) {
		if(file == null){
			return null ; 
		}
		if(!file.exists()){
			return null ; 
		}
		if(!file.isFile()){
			return null ; 
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return parse(is);
	}
	
	
	

}
