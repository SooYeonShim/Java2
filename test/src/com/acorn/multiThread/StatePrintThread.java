package com.acorn.multiThread;

public class StatePrintThread extends Thread{
	private Thread targetThread;
	
	public StatePrintThread(Thread targetThread) {
		this.targetThread = targetThread;
		
	}
	
	@Override
	public void run() {
		while(true) {//ë¬´í•œë£¨í”„
			//Target ?Š¤? ˆ?“œ?˜ ?˜„?¬ ?‹œ? ?˜ ?ƒ?ƒœë¥? ?•Œ?•„?ƒ„
			Thread.State state = targetThread.getState();
			System.out.println("??ê²? ?Š¤? ˆ?“œ ?ƒ?ƒœ: "+ state);
			
			//ë§Œì•½ ?Š¤? ˆ?“œ ?ƒ?ƒœê°? NEW?´ë©? ?Š¤? ˆ?“œ êµ¬ë™
			if(state == Thread.State.NEW) {
				targetThread.start();
			}//if
			
			//ë§Œì¼, Target ?Š¤? ˆ?“œ?˜ ?ƒ?ƒœê°? ì¢…ë£Œ?ƒ?ƒœ?´ë©? ë¬´í•œë£¨í”„ë¥? ë¹ ì ¸?‚˜?˜´
			if(state == Thread.State.TERMINATED) {
				break;
			}//if
			
			try {
				
			}catch(Exception e) {
				
			}
		}//while
		
	}
}
