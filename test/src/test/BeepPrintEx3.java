package test;

import java.awt.Toolkit;

public class BeepPrintEx3 {
	public static void main(String[] args) {
		Runnable beepTask = new BeepTask();
		Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			
			
			for(int i=0; i<5; i++) {
				System.out.println("¶ò");
				try {Thread.sleep(500L);
				}catch(Exception e) {
					
					}
				}

			}
		
		
		});
		thread.start();
	}
}
