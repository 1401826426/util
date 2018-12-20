package util.future;

public interface RPromise<V> extends RFuture<V> {
	
	public void setFailure(Throwable cause) ; 
	
	public void setSuccess(V result) ; 
	
}
