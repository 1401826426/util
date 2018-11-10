package util.asyn;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SingeThreadExecutor {
	
	private Queue<Runnable> taskQueue ; 
	
	private Thread taskThread ; 
	
	private Queue<ScheduledTask> sheduledTaskQueue = new PriorityQueue<>() ; 
	
	private AtomicInteger state ; 
	
	private enum State{
		NOT_START(0),
		STARTED(1),
		SHUTTING_DOWN(2),
		SHUTED_DOWN(3) ; 
		private int state ; 
		private State(int state){
			this.state = state ; 
		}
	}
	
	protected Queue<Task> newTaskQueue(){
		return new LinkedBlockingQueue<>() ; 
	}
	
	public boolean inTaskThread(){
		return taskThread == Thread.currentThread() ;  
	}
	
	public void execute(Runnable task){
		boolean inTaskThread = inTaskThread() ; 
		if(inTaskThread){
			taskQueue.add(task) ; 
		}else{
			startThread() ;
			if(state.get() != State.STARTED.state){
				throw new RuntimeException("not started") ; 
			}
			taskQueue.add(task) ; 
 		}
	}

	
	public ScheduledTask shedule(Runnable task,TimeUnit unit,long delay){
		assert unit != null ; 
		long delayMills = unit.toMillis(delay) ; 
		final ScheduledTask sheduledTask = new ScheduledTask(task,delayMills) ;
		boolean inTaskThread = inTaskThread() ; 
		if(inTaskThread){
			this.sheduledTaskQueue.add(sheduledTask) ; 
		}else{
			execute(new Runnable() {
				
				@Override
				public void run() {
					if(sheduledTask.isIdle()){						
						sheduledTaskQueue.add(sheduledTask) ;
					}
				}
			}) ; 
		}
		return sheduledTask ; 
	}

	
	private class TimeCheckExecutor implements Runnable{
		@Override
		public void run() {
			
		}
	}
	
	private void startThread() {
		
	}
	
}




































