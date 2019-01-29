package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;




public class  MotorControllerHandler implements CIMMessageHandler {
	
	
	public boolean process(Message message){
		RobotWrapper.getInstance().controlMotor(message.getContent());
		return true;
	}

	 
}
