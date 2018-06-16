package util.express;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import util.express.exception.ExpressException;
import util.express.exception.ParseException;
import util.express.node.CompositeNode;
import util.express.node.ConstantNode;
import util.express.node.FuncNode;
import util.express.node.Node;
import util.express.node.OperatorNode;
import util.express.node.RootNode;
import util.express.node.VarNode;
import util.express.node.logic.CheckControllerNode;
import util.express.node.logic.ElseNode;
import util.express.node.logic.IfNode;
import util.express.op.OperatorManager;
import util.str.StringUtils;

public class Express {
	
	private Node root ; 
	
	private char[] chs ; 
	
	private int cursor ;
	
	Stack<Node> stack ; 
	
	
	
	private Express(String express) throws ParseException{
		chs = express.toCharArray() ;
		root = new RootNode() ;
		cursor = 0 ; 
		stack = new Stack<Node>()  ; 
		List<Node> nodes = parse() ; 
		root = new RootNode() ; 
		root.addChilderns(nodes);
	}
	
	
	public List<Node> parse() throws ParseException { 
		List<Node> list = new ArrayList<Node>() ;
		List<Node> result = new ArrayList<Node>() ; 
		Node checkControllerNode = null ; 
		while(!isEnd()){
			String word = popNextWord();
			if("if".equals(word) || "elseif".equals(word)){
				if("if".equals(word)){					
					checkControllerNode = new CheckControllerNode() ;
					list.add(checkControllerNode) ; 
				}
				if(checkControllerNode == null){
					throw new ParseException("elseif前面没有if节点") ;
				}
				String nWord = peekNextWord() ; 
				if(!nWord.equals("(")){
					throw new ParseException("位置"+cursor+"的if后面接的不是(") ; 
				}
				popNextWord();
				List<Node> tmp = parse() ;
				if(tmp.size() != 1){
					throw new ParseException("if的判断中不应该有;") ;
				}
				Node judgeNode =  tmp.get(0) ; 
				String nnWord = peekNextWord() ; 
				if(!nnWord.equals("{")){
					throw new ParseException("位置"+cursor+"的if节点后面没有{") ;
				}
				popNextWord();
				List<Node> exeNodes = parse() ; 
				CompositeNode compositeNode = new CompositeNode() ; 
				compositeNode.addChilderns(exeNodes);
				Node node = new IfNode() ;
				node.addChildern(judgeNode);
				node.addChildern(compositeNode);
				checkControllerNode.addChildern(node);
			}else if("else".equals(word)){
				if(checkControllerNode == null){
					throw new ParseException("else前面没有if节点") ;
				}
				String nnWord = peekNextWord() ; 
				if(!nnWord.equals("{")){
					throw new ParseException("位置"+cursor+"的else节点后面没有{") ;
				}
				popNextWord();
				List<Node> exeNodes = parse() ;
				CompositeNode compositeNode = new CompositeNode() ; 
				compositeNode.addChilderns(exeNodes);
				Node node = new ElseNode() ;
				node.addChildern(compositeNode);
				checkControllerNode.addChildern(node);
			}else if("{".equals(word) || "(".equals(word)){
				List<Node> nodes = parse() ; 
				Node compositeNode = new CompositeNode() ; 
				compositeNode.addChilderns(nodes);
				list.add(compositeNode) ; 
			}else if(")".equals(word) || "}".equals(word)){
				break;  
			}else if(";".equals(word) || ",".equals(word)){
				Node node = adjustOpNode(list) ;
				list.clear(); 
				result.add(node) ;
			}else if(isOp(word)){
				String op = word; 
				if("|".equals(word)){
					String nWord = peekNextWord() ; 
					if("|".equals(nWord)){
						popNextWord() ; 
						op += "|" ; 
					}
				}else if(">".equals(word) || "<".equals(word) || "=".equals(word)){
					String nWord = peekNextWord() ; 
					if("=".equals(nWord)){
						popNextWord();
						op += '=' ; 
					}
				}else if("&".equals(word)){
					String nWord = peekNextWord() ; 
					if("&".equals(nWord)){
						popNextWord() ; 
						op += "&" ; 
					}
				}else if("!".equals(word)){
					String nWord = peekNextWord() ; 
					if("=".equals(nWord)){
						popNextWord() ; 
						op+="=" ; 
					}
				}
				OperatorNode node = new OperatorNode(op) ; 
				list.add(node) ; 
			}else if(StringUtils.getInstance().isNumber(word)){
				Number number = StringUtils.getInstance().parseNumber(word) ;
				ConstantNode node = new ConstantNode(number) ; 
				list.add(node) ; 
			}else{
				String nWord = peekNextWord() ; 
				if("(".equals(nWord)){
					popNextWord() ; 
					List<Node> paramNode = parse() ;
					FuncNode funcNode = new FuncNode(word) ; 
					funcNode.addChilderns(paramNode);
					list.add(funcNode) ; 
				}else{
					Node node = new VarNode(word) ;
					list.add(node) ; 
				}
			}
		}
		if(list.size() != 0){
			Node node = adjustOpNode(list) ;
			list.clear(); 
			result.add(node) ; 
		}
		return result ; 
	}


	private boolean isOp(String word) {
		return OperatorManager.isOpPrefix(word) ; 
	}


	private Node adjustOpNode(List<Node> list) throws ParseException {
		List<Node> nList = new ArrayList<Node>() ; 
		nList.addAll(list) ; 
		for(int i = 1;i <= OperatorNode.maxlv;i++){
			Iterator<Node> it = nList.iterator() ;
			Node pre = null ; 
			Set<Node> remove = new HashSet<Node>() ; 
			while(it.hasNext()){
				Node node = it.next() ;  
				if(node instanceof OperatorNode){
					OperatorNode opNode = (OperatorNode)node ; 
					if(opNode.lv() == i){
						if(!it.hasNext()){
							throw new ParseException(opNode.getOp() + "运算符后面没有操作数") ; 
						}
						int opNum = opNode.pNum() ;
						if(opNum == 2){
							if(pre == null){
								if(!opNode.isMinus()){									
									throw new ParseException(opNode.getOp() + "运算符前面没有操作数") ;
								}
							}
							if(pre != null){
								opNode.addChildern(pre);								
								remove.add(pre) ; 
							}
						}
						Node nNode = it.next() ; 
						opNode.addChildern(nNode);
						remove.add(nNode) ; 
					}
				}
				pre = node ; 
			}
			nList.removeAll(remove) ; 
		}
		CompositeNode resultNode  = new CompositeNode() ; 
		resultNode.addChilderns(nList);
		return resultNode ; 
	}


	private boolean isEnd() {
		return cursor >= chs.length;
	}


	private String peekNextWord(){
		return nextWord(true) ; 
	}
	
	private String popNextWord(){
		return nextWord(false) ; 
	}


	private String nextWord(boolean peek) {
		StringBuilder sb = new StringBuilder("") ; 
		int num = 0 ; 
		while(!isEnd()){
			char ch = peek() ;
			boolean ret = false ; 
			switch(ch){
			case '+':
			case '-':
			case '*':
			case '/':
			case '(':
			case ')':
			case '{':
			case '}':
			case '!':
			case '&':
			case '=':
			case '>':
			case '<':
			case '|':
			case ';':
			case ',':
				if(sb.length() == 0){
					pop() ; 
					num++ ; 
					sb.append(ch); 
				}
				ret = true ; 
				break ; 
			case ' ':
				pop() ;
				if(sb.length() != 0){
					ret = true ; 
				}
				break ; 
			default:
				sb.append(pop()); 
				num++ ; 
				break ; 
			}
			if(ret){
				if(peek){
					for(int i = 0;i < num;i++){
						back() ; 
					}
				}
				return sb.toString() ; 
			}
		}
		if(sb.length() != 0){
			return sb.toString() ; 
		}
		return null ; 
	}

	private char back(){
		return chs[--cursor] ; 
	}

	private char pop() {
		return chs[cursor++] ; 
 	}

	private char peek() {
		return chs[cursor];
	}


	public static Express express(String express) throws ParseException{
		return new Express(express) ; 
	}
	
	
	public Number execute(Context context){
		try{			
			ExpressResult result = root.express(context) ; 
			return result.getNumber() ; 
		}catch(ExpressException e){
			e.printStackTrace(); 
			return 0 ; 
		}
	}
	
	
	
	public void printNodeTree(){
		printNodeTree(root , "") ; 
	}
	
	private void printNodeTree(Node root , String pre) {
		if(root == null){
			return  ; 
		}
		String str = root.toString() ;
		System.err.println(pre+str);
		StringBuilder sb = new StringBuilder(pre) ;
		if(str.length() >= 1){
			sb.append("#") ; 
		}
		for(int i = 1;i < str.length();i++){
			sb.append(".") ; 
		}
		for(Node node:root.getChildrens()){
			printNodeTree(node,sb.toString());
		}
	}


	public static void main(String[] args) throws ParseException {
//		String testExpress = "if(a>b){c=d}elseif(e>f){ggg=jjj}else{abe=789*123+789}" ;
//		String testExpress = "a=b+1*2;if(a!=b){d=eft}else{g=okn};asw=(func(456)*func(a,b)+567/123)+10*123";
//		String testExpress = "a=(-1)*123" ; 
		String testExpress = "1234-func(123)" ; 
		System.err.println(testExpress);
		Express.express(testExpress).printNodeTree();
	}
}














