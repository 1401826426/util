package util.timer;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Timer {
	
	public void shedule(Task task,TimeUnit timeUnit,long time) ; 
	
	public Set<Task> stop() ; 
}
