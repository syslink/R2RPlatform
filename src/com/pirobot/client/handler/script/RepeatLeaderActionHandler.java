package com.pirobot.client.handler.script;

import com.pirobot.client.global.Global;
import com.pirobot.client.model.ScriptCommand;



public class  RepeatLeaderActionHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command){
		Global.switchRepeatingLeader(true);
		return true;
	}

	 
}
