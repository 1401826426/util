package util.str;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternUtils {
	
	private final static String STR_MAIL_PATTERN = "^\\w+@\\w+\\.\\w+$";
	  
	private final static String STR_TELEPHONE_PATTERN = "^1\\d{10}$" ; 
	 
	private final static String STR_URL_PATTERN = "^https?://\\w+(\\.\\w+)*(/\\w+)*(\\?\\w+=\\w+(&\\w+=\\w+)*)?$" ; 
	
	private final static String HAS_SAME_CHAR_PATTERN = ".*(.).*\\1.*" ; 
	
	private static Map<String,Pattern> patterns = new HashMap<String,Pattern>() ; 
	
	public static Pattern getMailPattern(){
		Pattern pattern = patterns.get("mailPattern") ; 
		if(pattern == null){
			pattern = Pattern.compile(STR_MAIL_PATTERN) ;
			patterns.put("mailPattern", pattern) ; 
		}
		return pattern ; 
	}
	
	public static Pattern getTelephonePattern(){
		Pattern pattern = patterns.get("telephonePattern") ; 
		if(pattern == null){
			pattern = Pattern.compile(STR_MAIL_PATTERN) ;
			patterns.put("telephonePattern", pattern) ; 
		}
		return pattern ; 
	}
	
	
	public static Pattern getSameCharPattern(){
		Pattern pattern = patterns.get("samePattern") ; 
		if(pattern == null){
			pattern = Pattern.compile(HAS_SAME_CHAR_PATTERN) ;
			patterns.put("samePattern", pattern) ; 
		}
		return pattern ; 
	}
	
	public static Pattern getUrlPattern(){
		Pattern pattern = patterns.get("urlPattern") ; 
		if(pattern == null){
			pattern = Pattern.compile(STR_MAIL_PATTERN) ;
			patterns.put("urlPattern", pattern) ; 
		}
		return pattern ; 
	}
	
	
	public static void patternPrintObjectTree(Pattern pattern){
		try{
			Class<?> clazz = pattern.getClass() ;
			Field field = clazz.getDeclaredField("matchRoot") ; 
			System.err.println(field.getType().getName());
			field.setAccessible(true);
			Method method = clazz.getDeclaredMethod("printObjectTree",field.getType()) ;
			method.setAccessible(true); 
			method.invoke(pattern,field.get(pattern));
		}catch(Exception e){
			
		}
	}
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(STR_URL_PATTERN) ; 
		String[] testUrls = new String[]{
			"arhhttp://aaa.baa.baa",
			"http://aaa.baa.baa" ,
			"https://aa.ba.ca" , 
			"http://aa.ba/ca/ad/ae" , 
			"https://aa.ab/cacc" ,
			"https://abc.def/sf?afg=def",
			"https://abc.def/sf?afg=def&aef=sad",
			"https://abc.def/sf?afg=def&aef=fdsf&sfew=213" 
		} ; 
		for(String url:testUrls){
			Matcher matcher = pattern.matcher(url) ; 
			System.err.println(matcher.find());
		}
		System.err.println("===========================");
		String[] testMails = new String[]{
				"abs@qq.com" ,
				"sfne@qwe.cm" , 
				"sldre@sjdfb",
				"wreirj",
				"wkerjrnt@163.com" , 
		} ; 
		pattern = Pattern.compile(STR_MAIL_PATTERN) ; 
		for(String mail:testMails){
			Matcher matcher = pattern.matcher(mail) ; 
			System.err.println(matcher.find());
		}
		pattern = Pattern.compile(STR_TELEPHONE_PATTERN) ;
		System.err.println("========================");
		String[] testphones = new String[]{
			"12345678901" , 
			"13248235864",
			"17474747747" ,
			"abc" , 
		};
		pattern = Pattern.compile(STR_TELEPHONE_PATTERN);
		for(String phone:testphones){
			Matcher matcher = pattern.matcher(phone) ;
			System.err.println(matcher.find());
		}
		
//		String s = " ".replaceFirst("\\s","%20") ; 
//		System.err.println(s);
		System.err.println(Pattern.compile("\\s").matcher(" 22 23  ").replaceAll("%20")) ; 
	}
	
}


























