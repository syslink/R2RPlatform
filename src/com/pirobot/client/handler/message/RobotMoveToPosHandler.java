package com.pirobot.client.handler.message;

import org.apache.commons.lang3.StringUtils;


import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.PosInfo;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;
 

public class RobotMoveToPosHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(RobotMoveToPosHandler.class);

	public boolean process(Message message){	
		JSONObject contentObj = JSONObject.parseObject(message.getContent());
		String posName = contentObj.getString(Constants.Action.POSNAME);
		String posInfoStr = contentObj.getString(Constants.Action.POSITION);
		PosInfo posInfo = null;
		if(!StringUtils.isEmpty(posInfoStr) && posInfoStr.equals("0"))
		{
			String[] position = posInfoStr.split(",|，");
			posInfo = new PosInfo();
			posInfo.posX = Float.parseFloat(position[0]);
			posInfo.posY = Float.parseFloat(position[1]);
			posInfo.a = Float.parseFloat(position[2]);
			posInfo.w = Float.parseFloat(position[3]);
		}
		RobotWrapper.getInstance().moveToPosition(posName, posInfo);
		return true;
	}
}
