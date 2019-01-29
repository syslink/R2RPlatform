package com.pirobot.client.handler.message;


import org.apache.commons.lang3.StringUtils;


import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;

public class TextMessageHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(TextMessageHandler.class);

	public boolean process(Message message){
		
		
		if(StringUtils.isBlank(message.getContent())){
			return false;
		}
		
		RobotWrapper.getInstance().getRobotActionIntegration().processText(message.getContent());		
		
		return true;
	}
	
	
}
