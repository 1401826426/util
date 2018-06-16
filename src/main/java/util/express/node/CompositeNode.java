package util.express.node;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;

public class CompositeNode extends Node{

	public CompositeNode() {
		super(NodeType.COMPOSITE_NODE);
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException {
		ExpressResult result = null ; 
		for(Node node:childrens){
			result = node.express(context) ; 
		}
		return result;
	}
	
	

}
