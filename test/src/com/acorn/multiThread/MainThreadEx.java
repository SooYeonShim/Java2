package com.acorn.multiThread;

public class MainThreadEx {
	
	
	public static void main(String[] args) {
		
	
	Calculator calculator = new Calculator();
	
	User1 user1 = new User1();
	user1.setCalculator(calculator);
	User1 user2 = new User1();
	user1.setCalculator(calculator);
	user1.start();
	user2.start();
	System.out.println();
		
	}
}
