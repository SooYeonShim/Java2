package com.acorn.multiThread;

public class ThreadB extends Thread{
	@Override
	public void run() {
		for(int i=0; i<2; i++) {
			System.out.println(getName() + "�? 출력?�� ?��?��");
		}
	}//run
}
