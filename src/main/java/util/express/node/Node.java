package util.express.node;

import java.util.ArrayList;
import java.util.List;

import util.express.Context;
import util.express.ExpressResult;
import util.express.exception.ExpressException;
import util.express.exception.IllegalChildernException;

public abstract class Node {
	
	protected NodeType type ; 
	
	protected List<Node> childrens ; 
	
	public Node(NodeType type){
		this.type = type ; 
		this.childrens = new ArrayList<Node>() ; 
	}
	
	public NodeType getType(){
		return this.type ; 
	}
	
	public void addChildern(Node node){
		this.childrens.add(node) ; 
	}
	
	public void addChilderns(List<Node> nodes){
		this.childrens.addAll(nodes) ; 
	}
	
	public List<Node> getChildrens(){
		return childrens ; 
	}
	
	public abstract ExpressResult express(Context context) throws ExpressException;

	@Override
	public String toString() {
		return "t="+type;
	} 
	
	public Node getChilern(int index) throws IllegalChildernException{
		if(childrens.size() <= index){
			throw new IllegalChildernException("childern size is less than " + index) ; 
		}
		return childrens.get(index) ; 
	}
	
	
	
}



























