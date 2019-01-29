package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;

public class RebootHandler implements CIMMessageHandler {

	public boolean process(Message message){
		
		RobotWrapper.getInstance().getRobotActionIntegration().reboot();
		return true;
	}
 
}
