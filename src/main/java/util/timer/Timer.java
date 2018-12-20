package util.timer;

import java.util.concurrent.TimeUnit;

public interface Timer {
	
	public TimerCancelable shedule(Task task,TimeUnit timeUnit,long time) ; 
	
	public void shutdown() ; 
}
