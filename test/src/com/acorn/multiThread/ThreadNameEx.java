package com.acorn.multiThread;

public class ThreadNameEx {

	public static void main(String[] args) {
		Thread mainThread= Thread.currentThread();
		System.out.println("?��로그?�� ?��?�� ?��?��?�� ?���?: "+mainThread.getName());
		
		ThreadA threadA = new ThreadA();
		threadA.start();
		System.out.println("?��?�� ?��?��?�� ?���?: "+ threadA.getName());
		
		ThreadB threadB = new ThreadB();
		threadB.start();	
		System.out.println("?��?�� ?��?��?�� ?���?: " + threadB.getName());
	}
}
