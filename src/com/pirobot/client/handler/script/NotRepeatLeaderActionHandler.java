package com.pirobot.client.handler.script;

import com.pirobot.client.global.Global;
import com.pirobot.client.model.ScriptCommand;


public class  NotRepeatLeaderActionHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command){
		Global.switchRepeatingLeader(false);
		return true;
	}

	 
}
