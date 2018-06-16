package util.express.node;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;

public class RootNode extends Node{

	public RootNode() {
		super(NodeType.ROOT);
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException{
		ExpressResult result = null ; 
		for(Node node:childrens){
			result = node.express(context) ; 
		}
		return result;
	}

}
