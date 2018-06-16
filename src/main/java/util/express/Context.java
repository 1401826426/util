package util.express;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import util.express.func.Func;
import util.express.func.InlineFuncManager;

public class Context {
	
	private Map<String,Number> map = new ConcurrentHashMap<String,Number>() ; 
	
	private Map<String,Func> funcs = new ConcurrentHashMap<String,Func>() ; 
	
	private InlineFuncManager inlineFuncManager = InlineFuncManager.getInstance(); 
	
	public void put(String key,Number value){
		map.put(key, value); 
	}
	
	public Number get(String key){
		return map.get(key) ; 
	}
	
	
	public Func getFunc(String name){
		Func func = funcs.get(name) ; 
		if(func != null){
			return func ; 
		}
		return  inlineFuncManager.getFunc(name) ; 
	}
	
	public void addFunc(Func func){
		funcs.put(func.getName(), func) ; 
	}
	
	
}







