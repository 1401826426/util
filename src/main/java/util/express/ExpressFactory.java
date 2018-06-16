package util.express;

import java.util.HashMap;
import java.util.Map;

public class ExpressFactory {
	
	private static ExpressFactory instance = new ExpressFactory() ; 
	
	public static ExpressFactory getInstance(){
		return instance ; 
	}
	
	public static interface IExpressFactory{
		Express getExpress(String express) ; 
	}
	
	class CacheExpressFactory implements IExpressFactory{
		private Map<String,Express> map = new HashMap<String,Express>() ;

		@Override
		public Express getExpress(String express) {
			if(map.containsKey(express)){
				return map.get(express) ; 
			}
			Express expr = ExpressFactory.this.getExpress(express) ;
			map.put(express, expr) ; 
			return expr ; 
		} 
	}

	class NoCacheExpressFactory implements IExpressFactory{

		@Override
		public Express getExpress(String express) {
			return  ExpressFactory.this.getExpress(express) ;
		}
	}
	
	public IExpressFactory getCacheExpressFactory(){
		return new CacheExpressFactory() ; 
	}
	
	public IExpressFactory getNoCacheExpressFactory(){
		return new NoCacheExpressFactory() ; 
	}
	
	
	private Express getExpress(String express){
		return Express.express(express)  ;
	}
	
	
}
