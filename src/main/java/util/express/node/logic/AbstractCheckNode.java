package util.express.node.logic;

import util.express.Context;
import util.express.exception.ExpressException;
import util.express.node.Node;
import util.express.node.NodeType;

public abstract class AbstractCheckNode extends Node{
	
	public AbstractCheckNode(NodeType type) {
		super(type);
	}

	public abstract boolean check(Context context) throws ExpressException; 
	
}
