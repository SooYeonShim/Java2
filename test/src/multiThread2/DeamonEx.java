package multiThread2;

public class DeamonEx {
	public static void main(String[] args) {
		
		
		AutoSave autoSaveThread = new AutoSave();
		autoSaveThread.setDaemon(true);
		autoSaveThread.start();
		try {
			Thread.sleep(3000);
		}catch(InterruptedException e){
			
		}
		System.out.println("메인 끝");
	}
	
}
