package com.pirobot.client.handler.script;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;




public class  CombineActionsHandler implements ScriptCommandHandler {
	
	@Override
	public boolean handle(ScriptCommand command) {
		String combineActions = command.getString(Constants.Action.COMBINEACTIONS);
		String extraContent = command.getString(Constants.Action.EXTRACONTENT);
		RobotWrapper.getInstance().excuteCombineActions(combineActions, extraContent != null ? extraContent : "");
		return true;
	}
 
}
