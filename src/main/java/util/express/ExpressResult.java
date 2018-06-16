package util.express;

public class ExpressResult {
	
	private String name ; 
	
	private Number number ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	public ExpressResult(String name, Number number) {
		super();
		this.name = name;
		this.number = number;
	}

	public ExpressResult(Number number) {
		super();
		this.number = number;
	}
	
	public int getIntValue(){
		return number == null ? 0 : number.intValue() ; 
	}
	
	public double getDoubleValue(){
		return  number == null ? 0 : number.doubleValue() ; 
	}
	
	public long getLongValue(){
		return number == null ? 0 : number.longValue(); 
	}
	
	public void notNum(){
		if(number == null){
			return ; 
		}
		if(getIntValue() == 0){
			this.number = 1 ; 
		}else{
			this.number = 0 ; 
		}
	}
}













