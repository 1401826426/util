package util.express.node;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;

public class VarNode extends Node{

	private String name ; 
	
	public VarNode(String name) {
		super(NodeType.VAR);
		this.name = name ; 
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException {
		Number number = context.get(name) ; 
		ExpressResult result = new ExpressResult(name,number) ; 
		return result;
	}

	@Override
	public String toString() {
		return "t="+type+",name="+name;
	}

	
	
}












