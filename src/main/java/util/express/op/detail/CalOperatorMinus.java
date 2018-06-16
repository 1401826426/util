package util.express.op.detail;

import java.util.List;

import util.collection.Pair;
import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.node.Node;
import util.express.op.Operator;

public class CalOperatorMinus extends Operator{

	@Override
	public ExpressResult execute(List<Node> childrens, Context context) throws ExpressException {
		if(childrens.size() > 1){
			Pair<ExpressResult,ExpressResult> pair = twoCommonCheck(childrens, context) ; 
			double value = pair.getLeft().getDoubleValue() + pair.getRight().getDoubleValue() ; 
			return new ExpressResult(value);
		}else{
			ExpressResult result = oneCommonCheck(childrens, context); 
			return new ExpressResult(-result.getNumber().doubleValue()) ; 
		}
		
	}

	@Override
	protected OperatorNumEnum getOperatorNumEnum() {
		return OperatorNumEnum.TWO;
	}

	@Override
	public int getLv() {
		return 3;
	}

}
