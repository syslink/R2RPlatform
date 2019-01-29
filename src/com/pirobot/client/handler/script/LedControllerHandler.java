package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;




public class  LedControllerHandler implements ScriptCommandHandler {
	
	@Override
	public boolean handle(ScriptCommand command) {
		String ledInfo = command.getString(Constants.Action.LEDCONTROLLER);
		RobotWrapper.getInstance().controlLed(ledInfo);
		return true;
	}
 
}
