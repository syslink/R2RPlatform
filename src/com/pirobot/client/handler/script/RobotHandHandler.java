package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.ThreadUtils;


public class  RobotHandHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command ){
		String leftAngel = command.getString(Constants.Action.LEFT_ANGEL);
		String rightAngel = command.getString(Constants.Action.RIGHT_ANGEL);
		int excuteTime = RobotWrapper.getInstance().hand(leftAngel, rightAngel);
		if(excuteTime > 0)
			ThreadUtils.sleep(excuteTime);
		return true;
	}
 
}
