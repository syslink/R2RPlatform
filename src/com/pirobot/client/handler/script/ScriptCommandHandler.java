package com.pirobot.client.handler.script;

import com.pirobot.client.model.ScriptCommand;


public interface ScriptCommandHandler {
    
	 boolean handle(ScriptCommand command);
}
