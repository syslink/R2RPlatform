package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;




public class  EmotionHandler implements ScriptCommandHandler {
	
	 
	 

	@Override
	public boolean handle(ScriptCommand command) {
		int duration = command.getIntValue(Constants.Action.DURATION);
		String emotion = command.getString(Constants.Action.EMOTION);
		RobotWrapper.getInstance().showEmotion(emotion, duration);
		return true;
	}
 
}
