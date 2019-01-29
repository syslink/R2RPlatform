package com.pirobot.client.handler.message;


import org.apache.commons.lang3.StringUtils;


import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.ProxyType;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.AbilityProxyManager;
import com.pirobot.client.tools.LoggerUtils;

public class ProxyTextMessageHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(ProxyTextMessageHandler.class);

	public boolean process(Message message){
		if(StringUtils.isBlank(message.getContent())){
			return false;
		}
		if(AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Ear).contains(message.getSender()))
			RobotWrapper.getInstance().getRobotActionIntegration().processText(message.getContent());		
		
		return true;
	}
	
	
}
