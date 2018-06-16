package util.express.func;

import java.util.List;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;

public abstract class Func {

	public abstract ExpressResult execute(List<ExpressResult> list, Context context) throws ExecuteException ; 
	
	public abstract String getName() ; 

}
