package util.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DefaultRPromise<V> implements RPromise<V>{
	
	private Object result ; 
	
	private short waiters = 0; 
	
	private List<RFutureListener<RFuture<V>>> listeners  = new ArrayList<>() ; 
	
	@Override
	public void setFailure(Throwable cause){
		if(setFailure0(cause)){
			notifyListeners();
			return ; 
		}
		throw new IllegalStateException("has completed") ; 
	}
	
	private boolean setFailure0(Throwable cause){
		if(isDone()){
			return  false; 
		}
		synchronized (this) {
			if(isDone()){
				return false; 
			}
			this.result = new CauseHolder(cause) ;
			if(this.waiters != 0){
				notifyAll();
			}
		}
		return true; 
	}
	
	@Override
	public void setSuccess(V result){
		if(setSuccess0(result)){
			notifyListeners();
			return ; 
		}
		throw new IllegalStateException("has completed") ; 
	}
	
	private boolean setSuccess0(V result){
		if(isDone()){
			return false; 
		}
		synchronized (this) {
			if(isDone()){
				return false; 
			}
			this.result = result ;
			if(this.waiters > 0){
				notifyAll(); 
			}
		}
		return true ; 
	}
	
	@Override
	public boolean isSuccess() {
		return result != null && !(result instanceof CauseHolder);
	}

	@Override
	public Throwable cause() {
		if(result == null){
			return null ; 
		}
		if(result instanceof CauseHolder){
			CauseHolder holder = (CauseHolder)result ; 
			return holder.cause ; 
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get() throws InterruptedException, ExecutionException {
		await() ; 
		if(result != null){
			if(result instanceof CauseHolder){
				throw new ExecutionException(((CauseHolder)result).cause) ; 
			}
			return (V)result ; 
		}
		throw new ExecutionException(((CauseHolder)result).cause) ; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(TimeUnit unit, long timeout) throws InterruptedException, ExecutionException {
		await(unit, timeout) ; 
		if(result != null){
			if(result instanceof CauseHolder){
				throw new ExecutionException(((CauseHolder)result).cause) ; 
			}
			return (V)result ; 
		}
		throw new ExecutionException(((CauseHolder)result).cause) ;
	}

	private boolean isDone(){
		return result != null ; 
	}
	
	@Override
	public boolean await() throws InterruptedException {
		if(isDone()){
			return true ; 
		}
		synchronized (this) {
			while(!isDone()){
				try{
					incWaiters();
					wait() ; 
				}finally{
					decWaiters();
				}
			}
		}
		return isDone();
	}
	
	@Override
	public boolean await(TimeUnit unit, long timeout) throws InterruptedException { 
		if(isDone()){
			return true ; 
		}
		if(unit == null || timeout <= 0){
			return isDone() ; 
		}
		long mills = unit.toMillis(timeout) ;
		long startTime = System.currentTimeMillis() ; 
		synchronized (this){
			while(!isDone()){
				try{
					incWaiters() ;
					wait(mills) ; 
					if(System.currentTimeMillis() - startTime <= mills){
						return isDone() ; 
					}
				}catch(InterruptedException e){
					throw e ; 
				}finally{
					decWaiters() ;
				}
			}
		}
		return isDone() ; 
	}

	private void incWaiters() {
		if(this.waiters == Short.MAX_VALUE){
			throw new RuntimeException("too match waiters") ; 
		}
		this.waiters++ ; 
	}

	private void decWaiters() {
		if(this.waiters == 0){
			throw new RuntimeException("error"); 
		}
		this.waiters--;
	}

	@Override
	public void addListener(RFutureListener<RFuture<V>> listener) {
		synchronized (this) {
			this.listeners.add(listener) ; 
		}
	}
	
	private void notifyListeners(){
		for(RFutureListener<RFuture<V>> listener:this.listeners){
			listener.onComplete(this);
		}
	}
	
	private static class CauseHolder{
		private Throwable cause;

		public CauseHolder(Throwable cause) {
			super();
			this.cause = cause;
		} 
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getNow() {
		if(this.result != null && this.result instanceof CauseHolder){
			return null ; 
		}
		if(this.result == null){
			return null ; 
		}
		return (V) this.result;
	}

	

}
