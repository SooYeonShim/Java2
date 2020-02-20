package com.acorn.multiThread;

import java.awt.Toolkit;

public class SleepEx {

	public static void main(String[] args){
		Toolkit tookit = Toolkit.getDefaultToolkit();
		for(int i=0; i<10 ; i++) {
			tookit.beep();
			try {Thread.sleep(300000);
			
			}catch(Exception e) {
				;;
			}
		}
	}
}
