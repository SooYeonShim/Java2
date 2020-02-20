package com.acorn.multiThread;

public class ThreadNameEx {

	public static void main(String[] args) {
		Thread mainThread= Thread.currentThread();
		System.out.println("?๋ก๊ทธ?จ ?? ?ค? ? ?ด๋ฆ?: "+mainThread.getName());
		
		ThreadA threadA = new ThreadA();
		threadA.start();
		System.out.println("?? ?ค? ? ?ด๋ฆ?: "+ threadA.getName());
		
		ThreadB threadB = new ThreadB();
		threadB.start();	
		System.out.println("?? ?ค? ? ?ด๋ฆ?: " + threadB.getName());
	}
}
