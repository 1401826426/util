package util.express.op.detail;

import java.util.List;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;
import util.express.exception.IllegalChildernException;
import util.express.node.Node;
import util.express.node.NodeType;
import util.express.op.Operator;

public class AssignOperator extends Operator{

	@Override
	public ExpressResult execute(List<Node> list, Context context) {
		if(list.size() != 2){
			throw new ExecuteException("=�Ĳ�������������") ; 
		}
		Node left = list.get(0) ; 
		if(left.getType() != NodeType.VAR){
			throw new IllegalChildernException("=����߲��Ǳ����ڵ�") ; 
		}
		ExpressResult rResult = list.get(1).express(context) ; 
		if(rResult.getNumber() == null){
			throw new IllegalChildernException("����û�и�ֵ�ı���") ; 
		}
		ExpressResult lResult = left.express(context) ;
		lResult.setNumber(rResult.getNumber());
		context.put(lResult.getName(), rResult.getNumber());
		return lResult;
	}
	
	@Override
	public int getLv() {
		return 8;
	}

	@Override
	protected OperatorNumEnum getOperatorNumEnum() {
		return OperatorNumEnum.TWO;
	}

}
