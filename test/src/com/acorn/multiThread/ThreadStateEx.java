package com.acorn.multiThread;

public class ThreadStateEx {
	
	public static void main(String[] args) {
		StatePrintThread statePrintThread = new StatePrintThread(new TargetThread());
		statePrintThread.start();
	}
}
