package util.express.op;

import java.util.HashMap;
import java.util.Map;

import util.express.op.detail.AssignOperator;
import util.express.op.detail.CalOPeratorAdd;
import util.express.op.detail.CalOperatorMinus;
import util.express.op.detail.CalOperatorMul;
import util.express.op.detail.LogicOperatorAnd;
import util.express.op.detail.LogicOperatorNot;
import util.express.op.detail.LogicOperatorOr;
import util.express.op.detail.RelationOperatorEqual;
import util.express.op.detail.RelationOperatorLarge;
import util.express.op.detail.RelationOperatorLargeOrEqual;
import util.express.op.detail.RelationOperatorLess;
import util.express.op.detail.RelationOperatorLessOrEqual;
import util.express.op.detail.RelationOperatorNotEqual;

public class OperatorManager {
	
	public static int MAX_LV = 8;
	
	static Map<String,Operator> ops ; 
	
	static{
		ops = new HashMap<String,Operator>() ; 
		ops.put("!",new LogicOperatorNot()); 
		ops.put("*",new CalOperatorMul()); 
		ops.put("/",new CalOperatorMinus()) ;  
		ops.put("+",new CalOPeratorAdd()) ; 
		ops.put("-",new CalOperatorMinus()); 
		ops.put("<",new RelationOperatorLess()) ; 
		ops.put("<=",new RelationOperatorLessOrEqual()); 
		ops.put(">",new RelationOperatorLarge()) ; 
		ops.put(">=",new RelationOperatorLargeOrEqual()); 
		ops.put("==",new RelationOperatorEqual()) ; 
		ops.put("!=",new RelationOperatorNotEqual());  
		ops.put("&&",new LogicOperatorAnd()); 
		ops.put("||",new LogicOperatorOr()) ; 
		ops.put("=",new AssignOperator()); 
		for(Operator op:ops.values()){
			MAX_LV = Math.max(MAX_LV,op.getLv()) ; 
		}
	}
	
	public static boolean isOpPrefix(String word){
		for(String key:ops.keySet()){
			if(key.startsWith(word)){
				return true ; 
			}
		}
		return false ; 
	}
	
	public static Operator getOperator(String op){
		return ops.get(op) ; 
	}
	
}












