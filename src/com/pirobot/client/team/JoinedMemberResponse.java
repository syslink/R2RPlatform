package com.pirobot.client.team;

import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.tools.UUIDTools;

public class JoinedMemberResponse {
	static public void responseSelfToNewMember(String newMemberId){
		
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_407);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(RobotWrapper.getInstance().getDeviceId());
		message.setReceiver(newMemberId);
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
	}
}
