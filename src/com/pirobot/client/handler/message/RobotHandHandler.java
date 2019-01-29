package com.pirobot.client.handler.message;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;
 

public class RobotHandHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(RobotHandHandler.class);

	public boolean process(Message message){	
		RobotWrapper.getInstance().excuteActionCmd(message.getContent());
		return true;
	}
	
}
