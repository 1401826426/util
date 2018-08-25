package util.clazz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
	
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("^\\s*List<(.*)>\\s*selectByExample\\((\\w+)\\s+(\\w+)\\);\\s*$"); 
		String value = "  List<Cat> selectByExample(CatExample example);" ; 
		Matcher matcher = pattern.matcher(value); 
		System.err.println(matcher.find()) ; 
		for(int i = 0;i <= matcher.groupCount();i++){
			System.err.println(matcher.group(i)) ; 
		}
	}
	
}
