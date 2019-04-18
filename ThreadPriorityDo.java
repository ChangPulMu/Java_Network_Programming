
class ThreadDo extends Thread{
	String name;
	public ThreadDo(String str, int prio) {
		name = str;
		setPriority(prio);
	}
 
	public void run() {
		for(int i=0;i<10;i++) { // more and more for good result
			System.out.println(name+": "+getPriority());
		}
		System.out.println(name+": finished");
	}
}

public class ThreadPriorityDo {
	public static void main(String[] args) {
		// MAX_PRIORITY(10), MIN_PRIORITY(1)
		ThreadDo thread1 = new ThreadDo("thread1", Thread.MAX_PRIORITY);
		ThreadDo thread2 = new ThreadDo("thread2", Thread.MIN_PRIORITY);
		
		//ThreadDo thread1 = new ThreadDo("thread1", Thread.MIN_PRIORITY);
		//ThreadDo thread2 = new ThreadDo("thread2", Thread.MAX_PRIORITY);
		
		thread1.start();
		thread2.start();
	}
}