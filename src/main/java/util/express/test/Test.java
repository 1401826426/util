package util.express.test;

import java.util.List;

import util.express.Context;
import util.express.Express;
import util.express.ExpressFactory;
import util.express.ExpressResult;
import util.express.exception.ExecuteException;
import util.express.exception.IllegalChildernException;
import util.express.func.Func;

public class Test {
	
	public static void main(String[] args) {
		test1() ; 
		System.err.println("==============================================================================");
		test2() ;
		System.err.println("==============================================================================");
		test3() ;
		System.err.println("==============================================================================");
		test4() ;
		System.err.println("==============================================================================");
		test5() ;
	}

	private static void test1() {
		String expressStr = "if(a>b){b=c}else{f=g}" ; 
		Express express = ExpressFactory.getInstance().getCacheExpressFactory().getExpress(expressStr) ;
//		express.printNodeTree();
		Context context = new Context() ; 
		context.put("a", 2);
		context.put("b", 1);
		context.put("c", 4);
		context.put("g", 5);
		System.err.println(express.execute(context));
		
	}
	
	private static void test2() {
		String expressStr = "if(a>b){b=c}else{f=g};a=(-1)*2;gh=max((-1),a)" ; 
		Express express = ExpressFactory.getInstance().getCacheExpressFactory().getExpress(expressStr) ;
//		express.printNodeTree();
		Context context = new Context() ; 
		context.put("a", 2);
		context.put("b", 1);
		context.put("c", 4);
		context.put("g", 5);
		System.err.println(express.execute(context));
		
	}
	
	private static void test3() {
		String expressStr = "if(a>b){b=c}else{f=g};a=(-1)*2;gh=max((-1),a);ggg=123+123*456" ; 
		Express express = ExpressFactory.getInstance().getCacheExpressFactory().getExpress(expressStr) ;
//		express.printNodeTree();
		Context context = new Context() ; 
		context.put("a", 2);
		context.put("b", 1);
		context.put("c", 4);
		context.put("g", 5);
		System.err.println(express.execute(context));
		
	}
	
	//与的优先级比或高 ,先执行与,然后再执行或,最终if中的判断正确
	private static void test4() {
		String expressStr = "if(a <= b && c <= g || max(1,2) == 2){b=c}else{f=g};" ; 
		Express express = ExpressFactory.getInstance().getCacheExpressFactory().getExpress(expressStr) ;
//		express.printNodeTree();
		Context context = new Context() ; 
		context.put("a", 2);
		context.put("b", 1);
		context.put("c", 4);
		context.put("g", 5);
		System.err.println(express.execute(context));
	}
	
	private static void test5() {
		String expressStr = "if(!(abs(max((-1),(-2))) >= 1)){b=c}else{f=g};" ; 
		Express express = ExpressFactory.getInstance().getCacheExpressFactory().getExpress(expressStr) ;
//		express.printNodeTree();
		Context context = new Context() ; 
		context.put("a", 2);
		context.put("b", 1);
		context.put("c", 4);
		context.put("g", 5);
		context.addFunc(new Abs());
		System.err.println(express.execute(context));
	}
	
	static class Abs extends Func{

		@Override
		public ExpressResult execute(List<ExpressResult> list, Context context) throws ExecuteException {
			if(list.size() != 1){
				throw new IllegalChildernException("abs函数的参数只能为1个") ;
			}
			ExpressResult result = list.get(0) ; 
			return new ExpressResult(Math.abs(result.getDoubleValue()));
		}

		@Override
		public String getName() {
			return "abs";
		}
		
	}

}
