package com.acorn.multiThread;

public class CalThread extends Thread{
	public CalThread(String name) {
		setName(name);
	}
	
	@Override
	public void run() {
		for(int i=0; i<20000000; i++);
		System.out.println(getName());
	}
}
