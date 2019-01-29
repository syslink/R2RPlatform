package com.pirobot.client.team;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.tools.UUIDTools;

public class MemberHeartBeatMessage {
	static public void sendHB(){
		
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_408);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(RobotWrapper.getInstance().getDeviceId());
		message.setReceiver(UDPTransmissionManager.allMembers);
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
	}
	
	static public void loopSendHB()
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new Runnable() {
			public void run() {
				try{
					while(true)
					{
						Thread.sleep(30000);
						sendHB();
					}
				}catch(Exception e){}
			}
		});
	}
}
