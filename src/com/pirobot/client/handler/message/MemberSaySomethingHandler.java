package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.model.Member;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.TeamMemberManager;

public class MemberSaySomethingHandler implements CIMMessageHandler{
	@Override
	public boolean process(Message message){
		String memberId = message.getSender();
		String content = message.getContent();
		Member member = TeamMemberManager.getInstance().getMemberAnyway(memberId);
		RobotWrapper.getInstance().getRobotActionIntegration().saySomethingToMe(member, content);
		return true;
	}
}
