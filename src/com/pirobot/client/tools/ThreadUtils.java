package com.pirobot.client.tools;

 
public class ThreadUtils {

	
	public static void sleep(long time) {
		
		if(time>0){
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
