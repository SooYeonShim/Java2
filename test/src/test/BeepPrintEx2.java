package test;

import java.awt.Toolkit;

public class BeepPrintEx2 {
	
	public static void main(String[] args) {
	Runnable beepTask = new BeepTask();
	Thread thread = new Thread(new Runnable() {

	@Override
	public void run() {
		for(int i=0; i<5; i++) {
			System.out.println("¶ò");
			try {Thread.sleep(500L);
			}catch(Exception e) {
				
				}
			}

		}
	
	
	});
	thread.start();
	
//	Thread thread1 = new Thread(()- > ) {
//		Toolkit toolkit1 = Toolkit.getDefaultToolkit();
//		toolkit1.beep();
//		for(int i=0; i<5; i++) {
//			System.out.println("¶ò");
//			try {Thread.sleep(500L);
//			}catch(Exception e) {
//				
//				}
//			}
//		
//	}
}
}
