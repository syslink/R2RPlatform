package com.pirobot.client.handler.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.network.HttpPostProcessor;




public class  EmbededScriptHandler implements ScriptCommandHandler {
	
	@Override
	public boolean handle(ScriptCommand command) {
		String sid = command.getString(Constants.Action.sid);
		String[] roles = command.getString(Constants.Action.roles).split(";");
		Map<String, String> rolesMap = new HashMap<String, String>();
		List<String> deviceIdList = new ArrayList<String>();
		String receivers = "";
		for(int i = 1; i <= roles.length; i++)
		{
			String deviceId = command.getString(Constants.Action.deviceIdPrefix + i);
			rolesMap.put(roles[i - 1], deviceId);
			deviceIdList.add(deviceId);
			receivers += deviceId;
			if(i != roles.length)
				receivers += ",";
		}
		HashMap<String,String> params = new HashMap<String ,String>();
		params.put("sid", sid);
		params.put("roles", rolesMap.toString());
		params.put("receiver", receivers);
		
		HttpPostProcessor.asyncHttpPost(params, Global.getInternalConfig("SCRIPT_EXECUTE_APIURL"));
		
		return true;
	}
	public void waitForEmbededScriptOver()
	{
		
	}
}
