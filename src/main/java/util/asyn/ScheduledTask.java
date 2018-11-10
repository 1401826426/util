package util.asyn;

public class ScheduledTask {

	private long deadLineTime ; 
	
	private Runnable task ;
	
	private volatile int state ; 
	
	private enum State{
		IDLE(0),
		DOING(1),
		SHUTDOWN(2);
		private int state ; 
		private State(int state){
			this.state = state ; 
		}
		
	}
	
	ScheduledTask(Runnable task, long delayMills) {
		this.deadLineTime = delayMills ; 
		this.task = task ;
		this.state = State.IDLE.state ; 
	}

	public void shutdown(){
		if(this.state != State.IDLE.state){
			throw new RuntimeException("can't shutdown") ; 
		}
		this.state = State.SHUTDOWN.state ; 
	}
	
	boolean isIdle(){
		return this.state == State.IDLE.state ; 
	}
	
	Runnable getTask(){
		return task ; 
	}
	
	boolean canDo(long time){
		return time >= deadLineTime && this.state == State.IDLE.state ; 
	}
}
