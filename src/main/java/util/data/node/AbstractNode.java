package util.data.node;

public abstract class AbstractNode implements IDataNode{
	
	protected String name ; 
	
	public AbstractNode(String name) {
		this.name = name ; 
	}

	public String getName(){
		return name ; 
	}
	
	protected String getBlank(int blank){
		StringBuilder sb = new StringBuilder("") ; 
		for(int i = 0;i < blank;i++){
			sb.append("  ") ; 
		}
		return sb.toString() ; 
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
