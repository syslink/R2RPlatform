package com.pirobot.client.handler.message;



import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.LoggerUtils;

public class  MemberActionHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(MemberActionHandler.class);	

	@Override
	public boolean process(Message message){
		logger.info("定位：收到队员的动作信息：" + message.getMid() + "--" + message.getContent());
		TeamMemberManager.getInstance().addMemberMoveAction(message.getSender(), message.getContent());
		return true;
	}
}
