package com.pirobot.client.handler.message;



import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.LoggerUtils;

public class  LeaderBroadcastSelfHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(LeaderBroadcastSelfHandler.class);	

	@Override
	public boolean process(Message message){
		logger.info("收到设置队长的信息：" + message.getMid() + "--" + message.getContent());
		String leaderId = JSONObject.parseObject(message.getContent()).getString("leaderId");
		TeamMemberManager.getInstance().setLeader(leaderId);
		return true;
	}
}
