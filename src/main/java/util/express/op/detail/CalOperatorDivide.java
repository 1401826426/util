package util.express.op.detail;

import java.util.List;

import util.collection.Pair;
import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;
import util.express.exception.ExpressException;
import util.express.node.Node;
import util.express.op.Operator;

public class CalOperatorDivide extends Operator{

	@Override
	public ExpressResult execute(List<Node> childrens, Context context) throws ExpressException {
		Pair<ExpressResult,ExpressResult> pair = twoCommonCheck(childrens, context) ; 
		ExpressResult l = pair.getLeft() ; 
		ExpressResult r = pair.getRight() ;
		if(r.getDoubleValue() == 0){
			throw new ExecuteException("/µÄ³ýÊýÎª0") ; 
		}
		double value = l.getDoubleValue()/r.getDoubleValue() ; 
		return new ExpressResult(value);
	}

	@Override
	protected OperatorNumEnum getOperatorNumEnum() {
		return OperatorNumEnum.TWO;
	}

	@Override
	public int getLv() {
		return 2;
	}

}
