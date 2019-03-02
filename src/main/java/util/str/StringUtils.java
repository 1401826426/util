package util.str;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	private static Pattern cutPattern = Pattern.compile("^(https?://\\w+(\\.\\w)*)/.*") ;

	
	public static boolean  isBlank(String str){
		return str == null || ("".equals(str.trim())) ; 
	}
	
	public static boolean isMail(String mail){
		Pattern pattern = PatternUtils.getMailPattern() ; 
		Matcher matcher = pattern.matcher(mail) ; 
		return matcher.find(); 
	}
	
	public static boolean isTelephone(String telephone){
		Pattern pattern = PatternUtils.getTelephonePattern() ;  
		Matcher matcher = pattern.matcher(telephone) ; 
		return matcher.find(); 
	}
	
	public static boolean isUrl(String url){
		Pattern pattern = PatternUtils.getUrlPattern() ; 
		Matcher matcher = pattern.matcher(url) ; 
		return matcher.find(); 
	}
	
	
	public static boolean isNumber(String str){
		if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
	}
	
	public static Number parseNumber(String str){
		try{
			return Integer.parseInt(str) ;
		}catch(Exception e){
			try{				
				return Long.parseLong(str) ; 
			}catch(Exception e1){
				return Double.parseDouble(str) ;
			}
		}
	}
	
	/**
	 * ��http�����host��ȡ��
	 * @param url
	 * @return
	 */
	public static String cutHostFromUrl(String url){
		if(cutPattern == null){
			cutPattern = Pattern.compile("^(https?://\\w+(\\.\\w)*)/.*") ;
		}
		Matcher matcher = cutPattern.matcher(url) ; 
		if(matcher.find()){
			String cut = matcher.group(1) ;
			return url.substring(cut.length()) ; 
		}
		return url; 
	}
	
	public static String firstCharUpper(String str){
		if(isBlank(str)){
			return str ; 
		}
		return getUpperChar(str.charAt(0)) + str.substring(1) ; 
	}

    public static char getUpperChar(char ch) {
		if(ch >= 'a' && ch <= 'z'){
			return (char) (ch - 'a' + 'A') ; 
		}
		return ch ; 
	}
	
    public static String firstCharLower(String str){
    	if(isBlank(str)){
			return str ; 
		}
		return getLowerChar(str.charAt(0)) + str.substring(1) ;
    }

	private static char getLowerChar(char ch) {
		if(ch >= 'A' && ch <= 'Z'){
			return (char) (ch - 'A' + 'a') ; 
		}
		return ch;
	}

	public static boolean isUpper(char ch){
		return ch >= 'A' && ch <= 'Z' ; 
	}
	
	public static boolean isLower(char ch){
		return ch >= 'a' && ch <= 'z' ; 
	}
	
	public static <T> String listToStr(List<T> list){
		StringBuilder sb = new StringBuilder("") ;
		boolean flag = false ; 
		if(list != null){
			for(T t:list){
				if(flag){
					sb.append(",") ; 
				}
				sb.append(t) ; 
				flag = true ; 
			}
		}
		return sb.toString() ; 
	}
	
	
	
	public static String[] splitByUpper(String value) {
		if(isBlank(value)){
			return new String[]{} ; 
		}
		List<String> list = new ArrayList<String>() ; 
		char[] chs = value.toCharArray() ;
		StringBuilder sb = new StringBuilder("") ; 
		for(char ch:chs){
			if(isUpper(ch)){
				String s = sb.toString() ; 
				if(!isBlank(s)){
					list.add(s) ; 
				}
				sb = new StringBuilder("");  
			}
			sb.append(ch) ; 
		}
		String s = sb.toString() ; 
		if(!isBlank(s)){
			list.add(s);
 		}
		String[] strs = new String[list.size()]; 
		list.toArray(strs) ; 
		return strs ; 
	}
	
	public static String formatJsonStr(String str){
		return null ; 
	}

	public static String javaToDb(String name) {
		StringBuilder sb = new StringBuilder("") ; 
		for(int i = 0;i < name.length();i++){
			char ch = name.charAt(i) ; 
			if(ch >= 'A' && ch <= 'Z'){
				if(i != 0){
					sb.append('_') ; 
					char cc = (char) (ch - 'A' + 'a') ; 
					sb.append(cc) ; 
				}
			}else{
				sb.append(ch) ; 
			}
 		}
		return sb.toString();
	}
	
	public static String dbToJava(String name){
		StringBuilder sb = new StringBuilder("") ; 
		for(int i = 0;i < name.length();i++){
			char ch = name.charAt(i) ; 
			if(ch == '_'){
				if(i < name.length()-1){					
					sb.append(name.charAt(i+1)-'a'+'A') ; 
				}
			}else{
				sb.append(ch) ; 
			}
		}
		return sb.toString() ; 
	}
	
	public static void main(String[] args){
		System.out.println(javaToDb("aBcDe")) ; 
	}
}





























