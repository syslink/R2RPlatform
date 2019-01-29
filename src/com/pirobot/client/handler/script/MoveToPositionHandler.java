package com.pirobot.client.handler.script;

import org.apache.commons.lang3.StringUtils;

import com.pirobot.client.global.Constants;
import com.pirobot.client.global.PosInfo;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.ThreadUtils;



public class  MoveToPositionHandler implements ScriptCommandHandler {
	
	public boolean handle(ScriptCommand command){
		String posName = command.getString(Constants.Action.POSNAME);
		String posInfoStr = command.getString(Constants.Action.POSITION);
		PosInfo posInfo = null;
		if(!StringUtils.isEmpty(posInfoStr) && posInfoStr.equals("0"))
		{
			String[] position = command.getString(Constants.Action.POSITION).split(",|，");
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
