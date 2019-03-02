package util.clazz;

import java.io.InputStream;

import util.data.DataParser;
import util.data.DataParserBuilder; 
public class DataParserTest {
	
	public static void main(String[] args) {
		InputStream is = DataParserTest.class.getClassLoader().getResourceAsStream("TestConf.xml") ; 
		DataParser parser = DataParserBuilder.getInstance().getXmlDataParser() ; 
		A a = parser.parse(A.class, is) ; 
		System.err.println(a);
	}
	
}
