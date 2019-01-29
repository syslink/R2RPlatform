 
package com.pirobot.client.tools;


import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.network.connector.UDPTransmissionManager;
public class UDPBordcastAppender extends AppenderSkeleton{
    public static String LOGMARK = "LOG4J:";
	@Override
	public void close() {}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		 if(Global.isDebugMode()&& event.getMessage()!=null){
			 StringBuilder builder = new StringBuilder();
			 builder.append(LOGMARK).append(RobotWrapper.getInstance().getDeviceId()).append(" ").append(event.getLocationInformation().getClassName()).append(".");
			 builder.append(event.getLocationInformation().getMethodName()).append("()").append(" ").append(StringUtils.getDatetime()).append("\r\n");
			 builder.append(event.getMessage().toString()).append("\r\n");
			 builder.append("---------------------------------------------------------------------------------------------");
			 UDPTransmissionManager.getInstance().send(builder.toString());
		 }
	}
}
