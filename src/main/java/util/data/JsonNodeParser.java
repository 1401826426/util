package util.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import util.data.node.DataNode;

public class JsonNodeParser extends AbstractDataNodeParser{

	@Override
	public DataNode parse(InputStream is) {
		StringBuilder sb = new StringBuilder("") ; 
		byte[] bytes = new byte[1024] ; 
		int len = -1 ; 
		try {
			while((len=is.read(bytes)) != -1){
				sb.append(new String(bytes,0,len)) ; 
			}
		} catch (IOException e) {
			throw new RuntimeException(e) ; 
		}
		return parse(sb.toString()) ;
	}

	@Override
	public DataNode parse(String s) {
		Stack<DataNode> stack = new Stack<>() ; 
		StringBuilder name = new StringBuilder("") ; 
		for(int i = 0;i < s.length();i++){
			char ch = s.charAt(i) ; 
			switch(ch){
				case ':':{
					char nextCh = s.charAt(i+1) ; 
					if(nextCh == '{'){
						
					}
				}
			}
		}
		return super.parse(s);
	}

	

}









