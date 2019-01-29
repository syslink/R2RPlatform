package com.pirobot.client.handler.message;



import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;
 

public class RobotCombineActionHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(RobotCombineActionHandler.class);

	public boolean process(Message message){
		JSONObject contentObj = JSONObject.parseObject(message.getContent());
		String combineActions = contentObj.getString(Constants.Action.COMBINEACTIONS);
		String extraContent = contentObj.getString(Constants.Action.EXTRACONTENT);
		extraContent = extraContent != null ? extraContent : "";
		if(contentObj.containsKey(Constants.Action.MUSIC))
		{
			boolean bPlayMusic = contentObj.getBooleanValue(Constants.Action.MUSIC);
			RobotWrapper.getInstance().excuteCombineActions(combineActions, extraContent, bPlayMusic);
		}
		else
		{
			RobotWrapper.getInstance().excuteCombineActions(combineActions, extraContent);
		}
		return true;
	}
}
