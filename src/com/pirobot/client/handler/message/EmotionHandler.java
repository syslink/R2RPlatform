package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;




public class  EmotionHandler implements CIMMessageHandler {
	
	
	public boolean process(Message message){
		RobotWrapper.getInstance().showEmotion(message.getContent(), 5000);
		return true;
	}

	 
}
