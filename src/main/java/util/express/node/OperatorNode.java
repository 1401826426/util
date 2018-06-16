package util.express.node;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.op.Operator;
import util.express.op.OperatorManager;

public class OperatorNode extends Node{
	
	public final static int maxlv = OperatorManager.MAX_LV;
	
	private String op ; 
	
	private Operator operator ; 
	
	public OperatorNode(String op) {
		super(NodeType.OPERATOR);
		this.op = op ; 
		this.operator = OperatorManager.getOperator(op) ; 
	}

	public String getOp(){
		return this.op ; 
	}
	
	public int pNum(){
		return operator.getPNum() ; 
	}
	
	public int lv(){
		return operator.getLv() ; 
	}
	
	
	@Override
	public ExpressResult express(Context context) throws ExpressException{
		return operator.execute(childrens,context) ; 
	}

	public boolean isMinus() {
		return "-".equals(op);
	}

	@Override
	public String toString() {
		return "t="+type+",op= "+op;
	}

	
	
	
}






