package util.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import util.data.node.IDataNode;
import util.data.node.ListNode;
import util.data.node.ObjectNode;
import util.data.node.ValueNode;
import util.str.StringUtils;

public class JsonNodeParser extends AbstractDataNodeParser{

	@Override
	public IDataNode parse(InputStream is) {
		StringBuilder sb = new StringBuilder("") ; 
		byte[] bytes = new byte[1024] ; 
		int len = -1 ; 
		try {
			while((len=is.read(bytes)) != -1){
				sb.append(new String(bytes,0,len)) ; 
			}
		} catch (IOException e) {
			throw new RuntimeException(e) ; 
		}
		return parse(sb.toString()) ;
	}

	static enum PARSE_STATE{
		IDLE,
		PARSE_VALUE,
		PARSE_QUOTE_VALUE,
		PARSE_QUOTE_NAME,
		LIST_OBJ_IDLE //在}或者]结束后,确保不会创建新的node
	}
	
	@Override
	public IDataNode parse(String s) {
		Stack<IDataNode> stack = new Stack<>() ; 
		StringBuilder nameBuilder = new StringBuilder("") ;
		StringBuilder valueBuilder = new StringBuilder("") ; 
		PARSE_STATE state = PARSE_STATE.IDLE ;
		IDataNode root = null ;
		for(int i = 0;i < s.length();i++){
			char ch = s.charAt(i) ;
			if(state == PARSE_STATE.PARSE_QUOTE_VALUE || state == PARSE_STATE.PARSE_QUOTE_NAME){
				if(ch == '"'){						
					state = PARSE_STATE.IDLE ; 
				}else{
					if(state == PARSE_STATE.PARSE_QUOTE_NAME){
						nameBuilder.append(ch) ; 
					}else if(state == PARSE_STATE.PARSE_QUOTE_VALUE){
						valueBuilder.append(ch) ; 
					}
				}
			}else{
				switch(ch){
					case ':':{
						state = PARSE_STATE.PARSE_VALUE ; 
						break ; 
					}
					case '{':{
						IDataNode node = new ObjectNode(nameBuilder.toString()) ;
						if(!stack.isEmpty()){
							IDataNode pre = stack.peek() ;
							pre.addNode(node);
						}
						stack.push(node) ;
						nameBuilder = new StringBuilder("") ;
						if(root == null){
							root = node ; 
						}
						state = PARSE_STATE.IDLE ;
						break ; 
					}
					case '[':{
						IDataNode node = new ListNode(nameBuilder.toString()) ;
						if(!stack.isEmpty()){
							IDataNode pre = stack.peek() ;
							pre.addNode(node);
						}
						nameBuilder = new StringBuilder("") ;
						if(root == null){
							root = node ; 
						}
						stack.push(node) ; 
						state = PARSE_STATE.PARSE_VALUE ;
						break ; 
					}
					case ',':{
						IDataNode node = stack.peek() ;
						if(node instanceof ListNode){
							if(state != PARSE_STATE.LIST_OBJ_IDLE){
								ListNode listNode = (ListNode) node ;
								ValueNode valueNode = new ValueNode(null) ;
								valueNode.setValue(valueBuilder.toString());
								listNode.addNode(valueNode);
							}
							state = PARSE_STATE.PARSE_VALUE;
						}else if(node instanceof ObjectNode){
							if(state != PARSE_STATE.LIST_OBJ_IDLE){
								ObjectNode objectNode = (ObjectNode)node;
								String name = nameBuilder.toString() ; 
								ValueNode valueNode = new ValueNode(name,valueBuilder.toString()) ; 
								objectNode.addDataNode(name,valueNode);
							}
							state = PARSE_STATE.IDLE ; 
						}
						nameBuilder = new StringBuilder("") ; 
						valueBuilder = new StringBuilder("") ; 
						break ; 
					}
					case '"':{
						if(state == PARSE_STATE.PARSE_VALUE){
							state = PARSE_STATE.PARSE_QUOTE_VALUE ; 
						}else{
							nameBuilder = new StringBuilder("") ; 
							state = PARSE_STATE.PARSE_QUOTE_NAME ;
						}
						break ; 
					}
					case ']':{
						IDataNode dataNode = stack.peek() ; 
						if(dataNode == null || !(dataNode instanceof ListNode)){
							throw new RuntimeException("illegal ],pos="+i) ; 
						}
						String value = valueBuilder.toString() ; 
						if(state != PARSE_STATE.LIST_OBJ_IDLE && !StringUtils.isBlank(value)){
							ListNode listNode = (ListNode)dataNode; 
							ValueNode valueNode = new ValueNode(null) ;
							valueNode.setValue(value);
							listNode.addNode(valueNode);
						}
						stack.pop() ; 
						nameBuilder = new StringBuilder("") ; 
						valueBuilder = new StringBuilder("") ;
						state = PARSE_STATE.LIST_OBJ_IDLE ; 
						break ; 
					}
					case '}':{
						IDataNode dataNode = stack.peek() ;
						if(dataNode == null || !(dataNode instanceof ObjectNode)){
							throw new RuntimeException("illegal },pos="+i) ; 
						} 
						if(state != PARSE_STATE.LIST_OBJ_IDLE && !StringUtils.isBlank(nameBuilder.toString())){//对于{}中的最后一个不是以,结尾,而是以}结尾的
							ObjectNode objectNode = (ObjectNode)dataNode ;
							String name = nameBuilder.toString();
							ValueNode valueNode = new ValueNode(name,valueBuilder.toString()) ; 
							objectNode.addDataNode(name, valueNode);
						}
						state = PARSE_STATE.LIST_OBJ_IDLE ; 
						nameBuilder = new StringBuilder("") ; 
						valueBuilder = new StringBuilder("") ; 
						stack.pop() ;
						break ; 
					}
					default:{
						if(state == PARSE_STATE.PARSE_VALUE){
							valueBuilder.append(ch) ; 
						}
					}
				}
			}
		}			
		return root;
	}
}









