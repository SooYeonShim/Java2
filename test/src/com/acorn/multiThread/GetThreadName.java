package com.acorn.multiThread;

public class GetThreadName {

	public static void main(String[] args) {
		Thread t= Thread.currentThread();
		System.out.println(" - t " +t);
		System.out.println(" - t's name" + t.getName());
	}	
}
