package com.pirobot.client.handler.message;



import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.LoggerUtils;

public class  LeaderActionHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(LeaderActionHandler.class);	

	@Override
	public boolean process(Message message){
		logger.info("定位：收到Leader的动作信息：" + message.getMid() + "--" + message.getContent());
		TeamMemberManager.getInstance().addLeaderMoveAction(message.getContent());
		if(Global.isRepeatingLeader())
		{
			RobotWrapper.getInstance().excuteActionCmd(message.getContent());
		}
		return true;
	}
}