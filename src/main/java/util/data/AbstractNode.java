package util.data;

public abstract class AbstractNode implements DataNode{
	
	protected String name ; 
	
	public AbstractNode(String name) {
		this.name = name ; 
	}

	public String getName(){
		return name ; 
	}
	
}
