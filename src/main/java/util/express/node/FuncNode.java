package util.express.node;

import java.util.ArrayList;
import java.util.List;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.func.Func;

public class FuncNode extends Node{
	
	private String name = null ; 
	
	public FuncNode(String name) {
		super(NodeType.FUNC) ;
		this.name = name ; 
	}

	@Override
	public ExpressResult express(Context context) throws ExpressException {
		Func func = context.getFunc(name) ; 
		if(func == null){
			throw new ExpressException(this + " " + name + " not exists") ; 
		}
		List<ExpressResult> list = new ArrayList<ExpressResult>() ;
		for(Node childern:childrens){
			ExpressResult result = childern.express(context) ; 
			if(result.getNumber() == null){
				throw new ExpressException("存在没赋值的变量") ; 
			}
			list.add(result) ; 
		}
		return func.execute(list,context);
	}

	@Override
	public String toString() {
		return "t="+type+",name="+name;
	}

	
	
}













