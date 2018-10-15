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

import util.data.node.IDataNode;
import util.data.node.ListNode;
import util.data.node.ObjectNode;
import util.data.node.ValueNode;
import util.str.StringUtils;

public class XmlNodeParser extends AbstractDataNodeParser{

	@Override
	public IDataNode parse(InputStream is) {
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

	private IDataNode parse(Element ele) {
		String name = ele.getAttribute("name") ; 
		String nodeName = ele.getNodeName() ;
		if("property".equals(nodeName)){
			String value = ele.getAttribute("value") ; 
			if(StringUtils.isBlank(value)){
				value = ele.getTextContent() ; 
			}
			IDataNode node  = new ValueNode(name,value) ;
			return node ; 
		}else if("array".equals(nodeName) || "list".equals(nodeName)){
			NodeList nl = ele.getChildNodes() ;
			List<IDataNode> list = new ArrayList<IDataNode>() ; 
			for(int i = 0;i < nl.getLength();i++){
				Node node = nl.item(i) ;
				if(node instanceof Element){
					Element element = (Element)node ; 
					IDataNode dataNode = parse(element) ; 
					list.add(dataNode) ; 
				}
			}
			ListNode listNode = new ListNode(name,list) ;
			return listNode ; 
		}else if("bean".equals(nodeName)){
			NodeList nl = ele.getChildNodes() ; 
			Map<String,IDataNode> map = new HashMap<String,IDataNode>() ; 
			for(int i = 0;i < nl.getLength();i++){
				Node node = nl.item(i); 
				if(node instanceof Element){
					Element element = (Element)node ; 
					IDataNode dataNode = parse(element) ; 
					map.put(dataNode.getName(),dataNode) ; 
				}
			}
			ObjectNode objectNode = new ObjectNode(name,map) ; 
			return objectNode ; 
		}
		return null ; 
	}


}






