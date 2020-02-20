package com.acorn.multiThread;

public class ThreadNameEx {

	public static void main(String[] args) {
		Thread mainThread= Thread.currentThread();
		System.out.println("?”„ë¡œê·¸?¨ ?‹œ?‘ ?Š¤? ˆ?“œ ?´ë¦?: "+mainThread.getName());
		
		ThreadA threadA = new ThreadA();
		threadA.start();
		System.out.println("?‘?—… ?Š¤? ˆ?“œ ?´ë¦?: "+ threadA.getName());
		
		ThreadB threadB = new ThreadB();
		threadB.start();	
		System.out.println("?‘?—… ?Š¤? ˆ?“œ ?´ë¦?: " + threadB.getName());
	}
}
