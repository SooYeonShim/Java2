package multiThread2;

public class WaitNotifyEx {
	public static void main(String[] args) {
		WorkObject sharedObject = new WorkObject();
		
		WaitThreadA threadA = new WaitThreadA(sharedObject);
		WaitThreadB threadB = new WaitThreadB(sharedObject);
		
		threadA.start();
		threadB.start();
		
	}
}
