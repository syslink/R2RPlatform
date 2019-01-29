package com.pirobot.client.handler.message;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.ProxyType;
import com.pirobot.client.model.Member;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.team.AbilityProxyManager;
import com.pirobot.client.team.TeamMemberManager;

public class AbilityProxyHandler implements CIMMessageHandler{
	@Override
	public boolean process(Message message){
		String content = message.getContent();
		JSONObject proxyInfoObj = JSONObject.parseObject(content);
		ProxyType proxyType = ProxyType.fromString(proxyInfoObj.getString("proxyType"));
		String subscriberId = proxyInfoObj.getString("subscriber");
		String proxyId = proxyInfoObj.getString("proxy");
		boolean enable = proxyInfoObj.getBooleanValue("enable");
		
		String selfId = RobotWrapper.getInstance().getDeviceId();
		
		if(subscriberId.equals(selfId))
		{
			if(enable)
				AbilityProxyManager.getInstance().addProxy(proxyId, proxyType);
			else
				AbilityProxyManager.getInstance().deleteProxy(proxyId, proxyType);
		}
		else if(proxyId.equals(selfId))
		{
			if(enable)
				AbilityProxyManager.getInstance().addSubscriber(subscriberId, proxyType);
			else
				AbilityProxyManager.getInstance().deleteSubscriber(subscriberId, proxyType);
		}
		return true;
	}
}
