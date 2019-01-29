package com.pirobot.client.handler.message;



import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.LoggerUtils;

public class  LeaderSettingHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(LeaderSettingHandler.class);


	@Override
	public boolean process(Message message){
		
		TeamMemberManager.getInstance().setLeader(RobotWrapper.getInstance().getDeviceId());
		logger.info("已被设置为Leader");
		return true;
	}
}
