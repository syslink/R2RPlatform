package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;




public class  MotorControllerHandler implements ScriptCommandHandler {
	
	@Override
	public boolean handle(ScriptCommand command) {
		String motorInfo = command.getString(Constants.Action.MOTORCONTROLLER);
		RobotWrapper.getInstance().controlMotor(motorInfo);
		return true;
	}
 
}
