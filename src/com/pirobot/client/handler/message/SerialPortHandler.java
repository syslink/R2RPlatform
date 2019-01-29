package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;

public class SerialPortHandler implements CIMMessageHandler {

	public boolean process(Message message){		
		byte[] data = message.getContent().getBytes();
		RobotWrapper.getInstance().getRobotActionIntegration().writeToSerialPort(data);
		return true;
	}
 
}
