package util.express.op;

import java.util.List;

import util.collection.Pair;
import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;
import util.express.exception.ExpressException;
import util.express.exception.IllegalChildernException;
import util.express.node.Node;

public abstract class Operator {
	
	private String name ; 
	
	public abstract ExpressResult execute(List<Node> childrens, Context context) throws ExpressException;

	public int getPNum(){
		OperatorNumEnum op = getOperatorNumEnum() ;
		return op.num ; 
	}
	
	protected abstract OperatorNumEnum getOperatorNumEnum() ; 

	public abstract int getLv() ;
	
	public ExpressResult oneCommonCheck(List<Node> childrens, Context context)throws ExpressException{
		if(childrens.size() != 1){
			throw new IllegalChildernException("逻辑非节点+" + name +  "后面的操作节点不是一个") ;
		}
		Node ch = childrens.get(0) ; 
		ExpressResult result = ch.express(context) ; 
		if(result.getNumber() == null){
			throw new ExecuteException("存在没赋值的变量") ;
		}
		return result ; 
	}
	
	public Pair<ExpressResult,ExpressResult> twoCommonCheck(List<Node> childrens, Context context)throws ExpressException{
		if(childrens.size() != 2){
			throw new IllegalChildernException("逻辑非节点后面" + name + "的操作数不是两个") ;
		}
		Node l = childrens.get(0) ; 
		ExpressResult lResult = l.express(context) ; 
		if(lResult.getNumber() == null){
			throw new ExecuteException("存在没赋值的变量") ;
		}
		Node r = childrens.get(1) ; 
		ExpressResult rResult = r.express(context) ; 
		if(rResult.getNumber() == null){
			throw new ExecuteException("存在没赋值的变量") ;
		}
		return new Pair<ExpressResult,ExpressResult>(lResult,rResult) ; 
	}
	
	public enum OperatorNumEnum{
		
		ONE(1),TWO(2) ;
		
		int num ; 
		
		private OperatorNumEnum(int num){
			this.num = num ; 
		}
	}
	
}
