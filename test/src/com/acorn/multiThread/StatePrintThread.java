package com.acorn.multiThread;

public class StatePrintThread extends Thread{
	private Thread targetThread;
	
	public StatePrintThread(Thread targetThread) {
		this.targetThread = targetThread;
		
	}
	
	@Override
	public void run() {
		while(true) {//λ¬΄νλ£¨ν
			//Target ?€? ?? ??¬ ?? ? ??λ₯? ???
			Thread.State state = targetThread.getState();
			System.out.println("??κ²? ?€? ? ??: "+ state);
			
			//λ§μ½ ?€? ? ??κ°? NEW?΄λ©? ?€? ? κ΅¬λ
			if(state == Thread.State.NEW) {
				targetThread.start();
			}//if
			
			//λ§μΌ, Target ?€? ?? ??κ°? μ’λ£???΄λ©? λ¬΄νλ£¨νλ₯? λΉ μ Έ??΄
			if(state == Thread.State.TERMINATED) {
				break;
			}//if
			
			try {
				
			}catch(Exception e) {
				
			}
		}//while
		
	}
}
