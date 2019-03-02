package util.json;

public class JsonDocument {
	
	StringBuilder sb = new StringBuilder("") ; 
	
	boolean first = true ; 
	
	public void startObject(){
		if(!first){
			sb.append(",") ; 
		}
		sb.append("{") ; 
		first = true ; 
	}
	
	public void createElement(String name,Object obj){
		if(!first){
			sb.append(",") ; 
		}
		sb.append(name+":"+obj) ; 
		first = false ; 
	}
	
	public void startObject(String name){
		if(!first){
			sb.append(",") ; 
		}
		sb.append(name + ":{") ; 
		first = true ; 
	}
	
	public void startArray(String name){
		if(!first){
			sb.append(",") ; 
		}
		sb.append(name + ":[") ; 
		first = true ; 
	}
	
	public void startArray(){
		if(!first){
			sb.append(",") ; 
		}
		sb.append("[") ; 
		first = true ; 
	}
	
	public void endArray(){
		sb.append("]") ;
		first = false ; 
	}
	
	public void endObject(){
		sb.append("}") ;
		first = false ; 
	}
	
	public void addJson(String json){
		sb.append(json) ; 
	}

	public byte[] toBytes() {
		return sb.toString().getBytes() ; 
	}

	public void append(String string) {
		sb.append(",") ; 
	}
	
}
