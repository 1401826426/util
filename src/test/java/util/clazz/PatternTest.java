package util.clazz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;




public class PatternTest {
	
	
	public static void main(String[] args) {
//		Pattern pattern = Pattern.compile("^\\s*List<(.*)>\\s*selectByExample\\((\\w+)\\s+(\\w+)\\);\\s*$"); 
//		String value = "  List<Cat> selectByExample(CatExample example);" ; 
//		Matcher matcher = pattern.matcher(value); 
//		System.err.println(matcher.find()) ; 
//		for(int i = 0;i <= matcher.groupCount();i++){
//			System.err.println(matcher.group(i)) ; 
//	    }
		
Pattern pattern = Pattern.compile("^[\\s\\S]*--start([\\s\\S]*)--end[\\s\\S]*$"); 
		
		String script = 
				"--deug heads\n"+
						"local redis = require 'redis_client' ; \n"+
						"local KEYS = {'1','2'}  ; \n"+
						"--start\n"+
						"local session = redis.call('hget','key_login',KEYS[1]); \n"+
						"if not session then  \n"+
						"       redis.call('hset','key_login',KEYS[1],KEYS[2]) ;\n"+
						"       session = redis.call('hget','key_login',KEYS[1]) ; \n"+
						"end \n"+
						"local maxVal = redis.call('zrevrange','session_timeout','0','0') \n"+
						"   local score = 1  \n"+
						"if #maxVal > 0 then \n"+
						"     local maxV = maxVal[1]; \n"+
						"     local add = tonumber(redis.call('zscore','session_timeout' ,maxV))  \n"+
						"     score = score + add; \n"+
						"end \n"+
						"redis.call('zadd','session_timeout',score,KEYS[1]) \n"+
						"return session ; \n"+
						"--end\n" ;
//		System.out.println(script);
		Matcher matcher = pattern.matcher(script) ; 
		matcher.find() ; 
		System.out.println(matcher.group(1)) ;
	}
	
	
	
	
	
}
