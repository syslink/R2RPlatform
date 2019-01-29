package com.pirobot.client.handler.message;


import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.handler.script.ScriptUtils;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.LoggerUtils;

public class ScriptHasReadyHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(ScriptHasReadyHandler.class);


	public boolean process(Message message){
		
		String scriptId = message.getContent();
		String roleId = message.getSender();
		ScriptUtils.processRoleReadyMsg(roleId, scriptId);
		return true;
	}
	
	
}
