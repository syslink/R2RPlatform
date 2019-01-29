package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.ThreadUtils;


public class  RobotRotateHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command){
		int duration = command.getIntValue(Constants.Action.DURATION);
		int radius = command.getIntValue(Constants.Action.RADIUS);
		int angle = command.getIntValue(Constants.Action.ANGLE);
		String orientation = command.getString(Constants.Action.ORIENTATION);
		
		
		int excuteTime = RobotWrapper.getInstance().rotate(orientation, angle, duration, radius);
		if(excuteTime > 0)
			ThreadUtils.sleep(excuteTime);

		return true;
	}

	 
}
