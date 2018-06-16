package util.express.node.logic;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;
import util.express.exception.ExpressException;
import util.express.node.Node;
import util.express.node.NodeType;

public class IfNode extends AbstractCheckNode{

	public IfNode() {
		super(NodeType.IF);
	}

	@Override
	public boolean check(Context context) throws ExpressException{
		Node node = getChilern(0) ; 
		ExpressResult result = node.express(context) ; 
		if(result.getNumber() == null){
			throw new ExecuteException("存在未赋值的变量") ; 
		}
		if(result != null && result.getIntValue()==1){
			return true; 
		}
		return false;
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException {
		Node node = getChilern(1) ; 
		ExpressResult result = node.express(context) ;
		return result;
	}

}











