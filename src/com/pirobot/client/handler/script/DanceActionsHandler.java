package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;




public class  DanceActionsHandler implements ScriptCommandHandler {
	
	@Override
	public boolean handle(ScriptCommand command) {
		String danceName = command.getString(Constants.Action.DANCE);
		String musicStr = command.getString(Constants.Action.MUSIC);
		
		boolean bMusic = musicStr != null ? musicStr.equals("是") : false;
		RobotWrapper.getInstance().excuteCombineActions(danceName, "", bMusic);
		return true;
	}
 
}
