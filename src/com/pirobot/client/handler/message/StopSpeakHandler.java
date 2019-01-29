package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.CommonUtils;

public class StopSpeakHandler implements CIMMessageHandler {

	public boolean process(Message message){
		
		RobotWrapper.getInstance().getRobotActionIntegration().stopSpeak();
		CommonUtils.killMusic();
		return true;
	}
 
}
