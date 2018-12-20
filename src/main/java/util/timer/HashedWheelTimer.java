package util.timer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HashedWheelTimer implements Timer{
	
	private volatile long startTime ; 
	
	private static final AtomicLong idG = new AtomicLong(1); 
	
	private Queue<HashWheelBucketNode> taskQue = new LinkedList<>() ; 
	
	private Queue<HashWheelBucketNode> cancelQue = new LinkedList<>() ; 
	
	private AtomicInteger pendingNums = new AtomicInteger(0) ; 
	
	private int maxPendingNum ;
	
	private volatile AtomicInteger state = new AtomicInteger(0); 
	
	private Thread workerThread ; 
	
	private CountDownLatch startWaiter = new CountDownLatch(1) ; 
	
	private HashWheelBucket[] buckets  ; 
	
	private long tick ; 
	
	private long tickInterval ; 
	
	private enum State{
		INITIAL(0),STARTED(1),WAIT_START(2),SHUTTINGDOWN(3),SHUTEDOWN(4);
		private int state ; 
		private State(int state){
			this.state = state ; 
		}
	}
	
	public HashedWheelTimer(int bucketLen,long tickInterval,int maxPendingNum){
		if(bucketLen < 2){
			throw new RuntimeException("bucketLen can't less than 2") ; 
		}
		this.buckets = new HashWheelBucket[bucketLen] ; 
		for(int i = 0;i < bucketLen;i++){
			this.buckets[i] = new HashWheelBucket() ; 
		}
		this.tickInterval = tickInterval ; 
		this.workerThread = new Thread(new Worker()) ; 
		this.maxPendingNum = maxPendingNum ; 
	}
	
	@Override
	public TimerCancelable shedule(Task task, TimeUnit unit, long time) {
		if(task == null){
			throw new NullPointerException("task not be null") ; 
		}
		if(unit == null){
			throw new NullPointerException("unit not be null") ; 
		}
		if(pendingNums.incrementAndGet() >= maxPendingNum){
			pendingNums.decrementAndGet() ; 
			throw new RejectedExecutionException("pending has full") ; 
		}
		start() ;
		long deadTime = System.currentTimeMillis() + unit.toMillis(time) ;
		long deadTick = (deadTime - startTime)/tickInterval ;
		HashWheelBucketNode node = new HashWheelBucketNode(task,deadTick) ; 
		this.taskQue.add(node) ; 
		return node ; 
	}

	public void start() {
		if(state.get() == State.INITIAL.state){
			synchronized (state) {
				if(state.get() == State.INITIAL.state){					
					workerThread.start();
					state.compareAndSet(State.INITIAL.state, State.WAIT_START.state) ; 
					System.out.println("started");
				}
			}
		}
		if(state.get() == State.SHUTTINGDOWN.state || state.get() == State.SHUTEDOWN.state){
			throw new RejectedExecutionException("has stop") ;
		}
		if(state.get() == State.WAIT_START.state){
			synchronized (state) {
				if(state.get() == State.WAIT_START.state){
					try {
						startWaiter.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			}
			
		}
		
	}
	
	public void shutdown(){
		if(state.get() == State.SHUTTINGDOWN.state){
			throw new RejectedExecutionException("has stop") ;
		}
		state.set(State.SHUTTINGDOWN.state);
		while(state.get() != State.SHUTEDOWN.state){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("shuted down");
	}

	
	private class Worker implements Runnable{

		@Override
		public void run() {
			startTime = System.currentTimeMillis();
			state.set(State.STARTED.state);
			startWaiter.countDown();  
			while(state.get() == State.STARTED.state){
				waitForNextTick() ; 
				queTobucket() ;
				clearCancelTak() ; 
				HashWheelBucket bucket = buckets[(int) (tick%buckets.length)] ; 
//				System.out.println(bucket);
				bucket.doTasks() ;
				tick++ ; 
			}
			state.set(State.SHUTEDOWN.state);
		}

		private void waitForNextTick() {
			while(state.get() == State.STARTED.state){
				long nextTick = startTime + (tick+1)*tickInterval ; 
				long sleepTime = nextTick - System.currentTimeMillis() ;
				if(sleepTime <= 0){
					break ; 
				}
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
			}
		}
		
	}

	public void queTobucket() {
		for(int i = 0;i < 10000;i++){
			HashWheelBucketNode node = taskQue.poll() ;
			if(node == null){
				break ; 
			}
			if(node.deadlineTick < tick){
				node.deadlineTick = tick ; 
			}
			int idx = (int) (node.deadlineTick%buckets.length) ; 
			buckets[idx].add(node) ; 
			node.bucket = buckets[idx] ; 
		}
	}

	public void clearCancelTak() {
		while(true){
			HashWheelBucketNode node = cancelQue.poll() ; 
			if(node == null){
				break ; 
			}
			node.cancel = true ; 
			node.remove() ;
		}
		
	}

	private class HashWheelBucket{

        private HashWheelBucketNode head ; 
        
        private HashWheelBucketNode tail ;
        
		public void doTasks() {
			HashWheelBucketNode node = this.head ;  
			while(node != null && state.get() == State.STARTED.state){
				if(node.deadlineTick > tick){
					node = node.next ; 
					continue ; 
				}
				node.task.run();
				HashWheelBucketNode next = node.next ; 
				remove(node);
				node = next; 
			}
		}

		public void add(HashWheelBucketNode node) {
			if(head == null){
				head = tail = node ; 
			}else{
				tail.next = node ; 
				node.front = tail ; 
				this.tail = node ; 
			}
		}
		
		private void remove(HashWheelBucketNode node){
			if(node.front == null){
				this.head = node.next; 
			}else{
				node.front.next = node.next ;
			}
			if(node.next == null){
				this.tail = node.front ; 
			}else{
				node.next.front = node.front ;
			}
			node.front = node.next = null ;
			node.bucket = null ;
		}
		
		@SuppressWarnings("unused")
		public void clear(Set<Task> set){
			while(true){
				HashWheelBucketNode node = poll() ;
				if(node == null){
					break ; 
				}
				if(node.hasDone() || node.hasCancel()){
					continue; 
				}
				set.add(node.task) ; 
			}
		}
		
		private HashWheelBucketNode poll(){
			if(this.head == null){
				return null ; 
			}
			if(this.head.next != null){
				this.head.next.front = null ; 
			}
			HashWheelBucketNode result = this.head ;
			this.head = this.head.next ; 
			result.next = null ; 
			result.bucket = null ; 
			return result ; 
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("") ; 
			HashWheelBucketNode p = this.head ;
			while(p != null && p != tail){
				sb.append(p+",") ; 
				p = p.next ; 
			}
			sb.append(tail+",") ; 
			return sb.toString() ; 
		}
		
		
		
	}
	
	private class HashWheelBucketNode implements TimerCancelable{
		
		private long id ; 
		
		private long deadlineTick ;  
		
		private HashWheelBucket bucket ; 
		
		private HashWheelBucketNode front ; 
		
		private HashWheelBucketNode next ;
		
		private Task task ; 
		
		private volatile boolean done ;  
		
		private volatile boolean cancel ; 
		
		public HashWheelBucketNode(Task task,long deadLineTick){
			this.task = task ;
			this.deadlineTick = deadLineTick ; 
			this.id = idG.incrementAndGet() ; 
		}
		
		public boolean hasCancel() {
			return cancel;
		}

		public boolean hasDone() {
			return done;
		}

		public void remove() {
			if(bucket != null){				
				bucket.remove(this);
			}
		}

		public void cancel(){
			if(!hasCancel()){				
				HashedWheelTimer.this.cancelQue.add(this) ; 
				this.cancel = true ; 
			}
		}
		
		@Override
		public String toString() {
			return "id=" + id + "&deadlineTick=" + deadlineTick;
		}
		
		
	}
	
	public static void main(String[] args) throws InterruptedException{
		HashedWheelTimer timer =  new HashedWheelTimer(10, 1000, 10000) ; 
		TimerCancelable cancelable = null ; 
		for(int i = 1;i <= 100;i++){
			final int pos  = i ; 
			TimerCancelable tmp = timer.shedule(new Task() {
				
				private int id = pos ; 
				
				@Override
				public void run() {
					System.out.println("run==============="+id);
				}
				
			},TimeUnit.SECONDS, i);
			if(i == 10){
				cancelable = tmp ; 
			}
		}
		
		Thread.sleep(2000); 
//		timer.shutdown();
		cancelable.cancel(); 
	}
	
}
















