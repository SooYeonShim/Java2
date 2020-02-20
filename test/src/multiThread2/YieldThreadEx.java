package multiThread2;

public class YieldThreadEx {
	public static void main(String[] args) {
		ThreadA threadA =new ThreadA();
		ThreadB threadB = new ThreadB();
		threadA.start();
		threadB.start();
		
		try {Thread.sleep(3000L);}
		catch(InterruptedException e) {
			;;
		}
		threadA.work = false;
		try {Thread.sleep(3000L);}
		catch(InterruptedException e) {
			;;
		}
		threadA.work = true;
		threadA.stop = true;
		threadB.stop = true;
	}
}
