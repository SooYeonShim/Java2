package multiThread2;

public class WaitThreadB extends Thread{
	private WorkObject workObject;
	public WaitThreadB(WorkObject workObject) {
		this.workObject = workObject;
	}
	
	@Override
	public void run() {
		for(int i=0; i<10; i++) {
			workObject.methodB();
		}
	}

}
