package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.JoinedMemberResponse;
import com.pirobot.client.team.TeamMemberManager;

public class NewMemberJoinedNoticeHandler implements CIMMessageHandler{

	public boolean process(Message message){
		
		String memberId = message.getContent();
		TeamMemberManager.getInstance().addMember(memberId);
		JoinedMemberResponse.responseSelfToNewMember(memberId);
		RobotWrapper.getInstance().getRobotActionIntegration().discoverNewMember(TeamMemberManager.getInstance().getMember(memberId));
		return true;
	}
}
