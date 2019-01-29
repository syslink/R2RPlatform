package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.team.TeamMemberManager;

public class MemberHeartBeatHandler implements CIMMessageHandler{
	@Override
	public boolean process(Message message){
		String memberId = message.getContent();
		TeamMemberManager.getInstance().updateMemberHBTime(memberId);
		return true;
	}
}
