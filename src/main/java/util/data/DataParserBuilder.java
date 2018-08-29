package util.data;

public class DataParserBuilder {
	
	private static DataParserBuilder instance = new DataParserBuilder() ; 
	
	public static DataParserBuilder getInstance(){
		return instance ; 
	}
	
	public DataParser getXmlDataParser(){
		XmlNodeParser xmlNodeParser = new XmlNodeParser() ; 
		DataParser dataParser = new DataParser(xmlNodeParser) ; 
		return dataParser; 
	}
	
	
}
