package multiThread2;

public class AutoSave extends Thread{
	public void save() {
		System.out.println("작업내용을 저장함");
	}//save
	
	@Override
	public void run() {
		while(true) {
			try {Thread.sleep(1000);}
			catch(InterruptedException e) {
				break;
			}
		}
		save();
	}
}
