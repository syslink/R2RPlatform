package com.pirobot.client.handler.message;


import org.apache.commons.lang3.StringUtils;


import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.model.Member;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.LoggerUtils;

public class MemberLearnQAHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(MemberLearnQAHandler.class);

	public boolean process(Message message){		
		if(StringUtils.isBlank(message.getContent())){
			return false;
		}
		JSONObject qaObject = JSONObject.parseObject(message.getContent());
		RobotWrapper.getInstance().getRobotActionIntegration().learnQA(qaObject.getString("question"), qaObject.getString("answer"));	
		
		return true;
	}
	
	
}
