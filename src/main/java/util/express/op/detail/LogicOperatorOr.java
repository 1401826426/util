package util.express.op.detail;

import java.util.List;

import util.collection.Pair;
import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.node.Node;
import util.express.op.Operator;

public class LogicOperatorOr extends Operator{

	@Override
	public ExpressResult execute(List<Node> childrens, Context context) throws ExpressException {
		Pair<ExpressResult,ExpressResult> pair = twoCommonCheck(childrens, context);
		int value = pair.getLeft().getIntValue() != 0 ||  pair.getRight().getIntValue() != 0 ? 1 : 0 ; 
		ExpressResult result = new ExpressResult(value) ; 
		return result;
	}

	@Override
	protected OperatorNumEnum getOperatorNumEnum() {
		return OperatorNumEnum.TWO ; 
	}

	@Override
	public int getLv() {
		return 7;
	}
	
}
