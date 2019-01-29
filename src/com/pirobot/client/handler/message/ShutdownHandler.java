package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;

public class ShutdownHandler implements CIMMessageHandler {

	public boolean process(Message message){
		
		RobotWrapper.getInstance().getRobotActionIntegration().shutdown();
		return true;
	}
 
}
