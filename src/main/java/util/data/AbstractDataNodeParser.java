package util.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import util.data.node.IDataNode;

public abstract class AbstractDataNodeParser implements NodeParser{

	@Override
	public IDataNode parse(File file) {
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
			return parse(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(is !=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
		}
		return null ; 
	}

	@Override
	public IDataNode parse(String s) {
		return parse(new ByteArrayInputStream(s.getBytes())) ; 
	}
	
	//暂时不管性能问题
	@Override
	public IDataNode parse(byte[] bytes) {
		return parse(new ByteArrayInputStream(bytes)) ; 
	}
	
}










