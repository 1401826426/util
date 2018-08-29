package util.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.str.StringUtils;

public class XmlNodeParser extends AbstractDataNodeParser{

	@Override
	public DataNode parse(InputStream is) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance() ; 
		try {
			DocumentBuilder db = dbf.newDocumentBuilder() ;
			Document doc = db.parse(is) ; 
			Element ele = doc.getDocumentElement() ; 
			return parse(ele) ; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private DataNode parse(Element ele) {
		String name = ele.getAttribute("name") ; 
		String nodeName = ele.getNodeName() ;
		if("property".equals(nodeName)){
			String value = ele.getAttribute("value") ; 
			if(StringUtils.isBlank(value)){
				value = ele.getTextContent() ; 
			}
			DataNode node  = new ValueNode(name,value) ;
			return node ; 
		}else if("array".equals(nodeName) || "list".equals(nodeName)){
			NodeList nl = ele.getChildNodes() ;
			List<DataNode> list = new ArrayList<DataNode>() ; 
			for(int i = 0;i < nl.getLength();i++){
				Node node = nl.item(i) ;
				if(node instanceof Element){
					Element element = (Element)node ; 
					DataNode dataNode = parse(element) ; 
					list.add(dataNode) ; 
				}
			}
			ListNode listNode = new ListNode(name,list) ;
			return listNode ; 
		}else if("bean".equals(nodeName)){
			NodeList nl = ele.getChildNodes() ; 
			Map<String,DataNode> map = new HashMap<String,DataNode>() ; 
			for(int i = 0;i < nl.getLength();i++){
				Node node = nl.item(i); 
				if(node instanceof Element){
					Element element = (Element)node ; 
					DataNode dataNode = parse(element) ; 
					map.put(dataNode.getName(),dataNode) ; 
				}
			}
			ObjectNode objectNode = new ObjectNode(name,map) ; 
			return objectNode ; 
		}
		return null ; 
	}

}






