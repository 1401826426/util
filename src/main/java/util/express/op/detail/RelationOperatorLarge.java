package util.express.op.detail;

import java.util.List;

import util.collection.Pair;
import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.node.Node;
import util.express.op.Operator;

public class RelationOperatorLarge extends Operator{

	@Override
	public ExpressResult execute(List<Node> childrens, Context context) throws ExpressException {
		Pair<ExpressResult,ExpressResult> pair = twoCommonCheck(childrens, context) ; 
		int value = pair.getLeft().getDoubleValue() > pair.getRight().getDoubleValue() ? 1 : 0 ; 
		return new ExpressResult(value);
	}

	@Override
	protected OperatorNumEnum getOperatorNumEnum() {
		return OperatorNumEnum.TWO ; 
	}

	@Override
	public int getLv() {
		return 4;
	}

}
