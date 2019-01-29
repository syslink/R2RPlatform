package com.pirobot.client.handler.message;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.model.Member;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.LoggerUtils;

public class MemberQuestionProcessHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(MemberQuestionProcessHandler.class);

	public boolean process(Message message){
		
		
		if(StringUtils.isBlank(message.getContent())){
			return false;
		}
		
		Member member = TeamMemberManager.getInstance().getMemberAnyway(message.getSender());
		JSONObject questionObj = JSONObject.parseObject(message.getContent());
		String[] unknownMemberIds = questionObj.getString("unknownMemberIds").split(",");
		String question = questionObj.getString("question");
		List<Member> unknownMemberList = new ArrayList<Member>();
		for(String memberId : unknownMemberIds)
		{
			Member unknownMember = TeamMemberManager.getInstance().getMemberAnyway(memberId);
			unknownMemberList.add(unknownMember);
		}
		RobotWrapper.getInstance().getRobotActionIntegration().processMemberQuestion(member, question, unknownMemberList);
		
		return true;
	}
	
	
}
