package com.acorn.multiThread;

public class StatePrintThread extends Thread{
	private Thread targetThread;
	
	public StatePrintThread(Thread targetThread) {
		this.targetThread = targetThread;
		
	}
	
	@Override
	public void run() {
		while(true) {//무한루프
			//Target ?��?��?��?�� ?��?�� ?��?��?�� ?��?���? ?��?��?��
			Thread.State state = targetThread.getState();
			System.out.println("??�? ?��?��?�� ?��?��: "+ state);
			
			//만약 ?��?��?�� ?��?���? NEW?���? ?��?��?�� 구동
			if(state == Thread.State.NEW) {
				targetThread.start();
			}//if
			
			//만일, Target ?��?��?��?�� ?��?���? 종료?��?��?���? 무한루프�? 빠져?��?��
			if(state == Thread.State.TERMINATED) {
				break;
			}//if
			
			try {
				
			}catch(Exception e) {
				
			}
		}//while
		
	}
}
