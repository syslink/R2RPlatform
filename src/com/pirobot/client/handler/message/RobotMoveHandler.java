package com.pirobot.client.handler.message;



import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;
 

public class RobotMoveHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(RobotMoveHandler.class);

	public boolean process(Message message){	
		RobotWrapper.getInstance().excuteActionCmd(message.getContent());
		return true;
	}
}
