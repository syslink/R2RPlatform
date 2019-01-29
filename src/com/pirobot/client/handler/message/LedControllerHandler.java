package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;




public class  LedControllerHandler implements CIMMessageHandler {
	
	
	public boolean process(Message message){
		RobotWrapper.getInstance().controlLed(message.getContent());
		return true;
	}

	 
}
