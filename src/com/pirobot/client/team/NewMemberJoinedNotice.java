package com.pirobot.client.team;



import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.tools.UUIDTools;

public class NewMemberJoinedNotice {
	static public void notifyOthers(){
		
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_406);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(RobotWrapper.getInstance().getDeviceId());
		message.setReceiver(UDPTransmissionManager.allMembers);
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
	}
}
