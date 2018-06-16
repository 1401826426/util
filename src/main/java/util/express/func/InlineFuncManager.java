package util.express.func;

import java.util.HashMap;
import java.util.Map;

public class InlineFuncManager {
	
	
	static Map<String,Func> funcs = new HashMap<String,Func>() ; 
	
	static{
		funcs.put("max",new MaxFunc()) ; 
	}
	
	private final static InlineFuncManager instance = new InlineFuncManager() ; 
	
	public static InlineFuncManager getInstance(){
		return instance ; 
	}
	
	
	public Func getFunc(String name) {
		return funcs.get(name) ; 
	}

}
