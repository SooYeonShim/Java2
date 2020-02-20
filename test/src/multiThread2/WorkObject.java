package multiThread2;

public class WorkObject {

	
	public synchronized void methodA() {
		System.out.println("ThreadA의 methodA() 작업실행");
		notify();
		try {
			wait(); //RUNNING->WAITING 상태로 빠짐
		}catch(InterruptedException e) {
			
		}
	}
	public synchronized void methodB() {
		System.out.println("ThreadB의 methodB() 작업실행");
		notify();
		try {
			wait(); //RUNNING->WAITING 상태로 빠짐
		}catch(InterruptedException e) {
			
		}
	}
}
