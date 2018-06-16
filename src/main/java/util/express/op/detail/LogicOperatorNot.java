package util.express.op.detail;

import java.util.List;

import util.express.Context;
import util.express.ExpressResult;
import util.express.node.Node;
import util.express.op.Operator;

public class LogicOperatorNot extends Operator{

	@Override
	public ExpressResult execute(List<Node> childrens, Context context) {
		ExpressResult result = oneCommonCheck(childrens, context);
		result.notNum(); 
		return result;
	}

	@Override
	protected OperatorNumEnum getOperatorNumEnum() {
		return OperatorNumEnum.ONE;
	}

	@Override
	public int getLv() {
		return 1;
	}
	
	
}
