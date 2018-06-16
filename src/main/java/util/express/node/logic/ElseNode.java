package util.express.node.logic;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.exception.IllegalChildernException;
import util.express.node.Node;
import util.express.node.NodeType;

public class ElseNode extends AbstractCheckNode{

	public ElseNode() {
		super(NodeType.ELSE);
	}

	@Override
	public boolean check(Context context) throws ExpressException {
		return true;
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException {
		if(childrens.size() != 1){
			throw new IllegalChildernException("else节点下面的节点个数不是1") ; 
		}
		Node node = childrens.get(0) ; 
		ExpressResult result = node.express(context) ; 
		return result;
	}

}








