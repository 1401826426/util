package util.express.node.logic;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.exception.IllegalChildernException;
import util.express.node.Node;
import util.express.node.NodeType;

public class CheckControllerNode extends Node{

	public CheckControllerNode() {
		super(NodeType.CHECK_CONTROLLER);
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException {
		for(Node childern:childrens){
			if(!(childern instanceof AbstractCheckNode)){
				throw new IllegalChildernException(childern + " not check node") ; 
			}
			AbstractCheckNode abstractLogicNode = (AbstractCheckNode)childern ; 
			if(abstractLogicNode.check(context)){
				return childern.express(context) ; 
			}
		}
		return null;
	}

}
