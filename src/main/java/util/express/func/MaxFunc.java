package util.express.func;

import java.util.List;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;

public class MaxFunc extends Func{

	@Override
	public ExpressResult execute(List<ExpressResult> list, Context context) throws ExecuteException {
		double value = -Double.MAX_VALUE ; 
		for(ExpressResult result:list){
			value = Math.max(value, result.getDoubleValue()) ; 
		}
		return new ExpressResult(value);
	}

	@Override
	public String getName() {
		return "max" ; 
	}

}
