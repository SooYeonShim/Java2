package com.acorn.multiThread;

public class Calculator {
	private int memory;
	
	public int getMemory() {
		return memory;
	}
	
	public void setMemory(int memory) {
		this.memory = memory;
		
		try {
			Thread.sleep(2000);
			
		}catch(InterruptedException e) {
			;;
		}
	}
}
