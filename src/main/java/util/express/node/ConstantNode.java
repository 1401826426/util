package util.express.node;

import util.express.Context;
import util.express.ExpressResult;

public class ConstantNode extends Node{
	
	private Number num ; 
	
	private ExpressResult result ; 
	public ConstantNode(Number num) {
		super(NodeType.CONSTANTS) ; 
		this.num = num ; 
		this.result = new ExpressResult(num) ;
	}

	@Override
	public ExpressResult express(Context context) {
		return this.result ; 
	}

	@Override
	public String toString() {
		return "t="+type+",num="+num;
	}
	
	
	

}
