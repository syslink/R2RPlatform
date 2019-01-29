package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.ThreadUtils;



public class  RobotMoveHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command){
		/*int distance = command.getIntValue(Constants.Action.DISTANCE);
		//int reangle = command.getIntValue(Constants.Action.REANGLE);
		int angle = command.getIntValue(Constants.Action.ANGLE);
		String orientation = command.getString(Constants.Action.ORIENTATION);
		
		
		Robot.getInstance().turn(orientation, angle, 500);
		Robot.getInstance().move(orientation, distance, 2000);
		ThreadUtils.sleep(2000);*/
		
		int duration = command.getIntValue(Constants.Action.DURATION);
		int distance = command.getIntValue(Constants.Action.DISTANCE);
		String orientation = command.getString(Constants.Action.ORIENTATION);
		
		int excuteTime = RobotWrapper.getInstance().move(orientation, distance, duration);
		if(excuteTime > 0)
			ThreadUtils.sleep(excuteTime);

		return true;
	}

	 
}
