package util.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public interface RFuture<V> {
	
	public boolean isSuccess() ; 
	
	public Throwable cause() ; 
	
	public V get() throws InterruptedException , ExecutionException; 
	
	public V get(TimeUnit unit,long timeout) throws InterruptedException,ExecutionException ; 
	
	public boolean await(TimeUnit unit,long timeout) throws InterruptedException;
	
	public boolean await() throws InterruptedException;
	
	public void addListener(RFutureListener<RFuture<V>> listener) ;
	
	public V getNow(); 
	
}
