package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;


public class  HeadTurnHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command){
		int duration = command.getIntValue(Constants.Action.DURATION);
		int angle = command.getIntValue(Constants.Action.ANGLE);
		String orientation = command.getString(Constants.Action.ORIENTATION);
				
		RobotWrapper.getInstance().turnHead(orientation, angle, duration);

		return true;
	}

	 
}
