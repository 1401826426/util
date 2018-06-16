package util.str;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	private static StringUtils instance = null ; 
	
	private Pattern cutPattern = null ; 
	
	public static StringUtils getInstance(){
		if(instance == null){
			synchronized (StringUtils.class) {
				if(instance == null){
					instance = new StringUtils() ; 
				}
			}
		}
		return instance ; 
	}
	
	public boolean  isBlank(String str){
		return str != null && (!"".equals(str.trim())) ; 
	}
	
	public boolean isMail(String mail){
		Pattern pattern = PatternUtils.getMailPattern() ; 
		Matcher matcher = pattern.matcher(mail) ; 
		return matcher.find(); 
	}
	
	public boolean isTelephone(String telephone){
		Pattern pattern = PatternUtils.getTelephonePattern() ;  
		Matcher matcher = pattern.matcher(telephone) ; 
		return matcher.find(); 
	}
	
	public boolean isUrl(String url){
		Pattern pattern = PatternUtils.getUrlPattern() ; 
		Matcher matcher = pattern.matcher(url) ; 
		return matcher.find(); 
	}
	
	
	public boolean isNumber(String str){
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
	
	public Number parseNumber(String str){
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
	 * 把http请求的host截取掉
	 * @param url
	 * @return
	 */
	public String cutHostFromUrl(String url){
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
}





























