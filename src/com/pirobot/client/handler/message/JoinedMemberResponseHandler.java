package com.pirobot.client.handler.message;

import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.team.TeamMemberManager;

public class JoinedMemberResponseHandler implements CIMMessageHandler{

	public boolean process(Message message){
		
		String memberId = message.getContent();
		TeamMemberManager.getInstance().addMember(memberId);
		return true;
	}
}
