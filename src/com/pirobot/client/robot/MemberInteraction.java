package com.pirobot.client.robot;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.ProxyType;
import com.pirobot.client.model.Member;
import com.pirobot.client.network.MessageSendProcessor;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.team.AbilityProxyManager;
import com.pirobot.client.tools.UUIDTools;

public class MemberInteraction {
	static public void sayHello(Member member, String content)
	{		
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_409);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(content);
		message.setReceiver(member.getId());
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}
	
	static public void saySomething(List<Member> memberList, String sentence)
	{
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_410);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(sentence);
		message.setReceiver(getMemberIds(memberList));
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}
	
	static public void sendMessage(List<Member> memberList, String content)
	{
		sendMessage(Constants.MessageAction.ACTION_411, memberList, content);
	}

	// 将听到的内容发送给订阅者
	static public void sendListenedMessage(String content)
	{
		Set<String> subscriberSet = AbilityProxyManager.getInstance().getSubscriberRobots(ProxyType.Ear);
		String subscribers = subscriberSet.toString();
		sendMessage(Constants.MessageAction.ACTION_101, subscribers.substring(1, subscribers.length() - 1), content);
	}

	static protected void sendMessage(String action, List<Member> memberList, String content)
	{
		sendMessage(action, getMemberIds(memberList), content);
	}
	
	// memberIds：成员id用英文逗号隔开
	static public void sendMessage(String action, String memberIds, String content)
	{
		Message message = new Message();
		message.setAction(action);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(content);
		message.setReceiver(memberIds);
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}
	
	static public void letAnotherAnswerQuestion(Member member, String question, List<Member> unknownMemberList)
	{
		JSONObject questionInfo = new JSONObject();
		questionInfo.put("question", question);
		String memberIds = "";
		for(Member m : unknownMemberList)
		{
			memberIds += "," + m.getId();
		}
		if(memberIds.length() > 1)
			memberIds = memberIds.substring(1);
		questionInfo.put("unknownMemberIds", memberIds);
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_413);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(questionInfo.toJSONString());
		message.setReceiver(member.getId());
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}
		
	static public void letAnotherLearnQA(Member member, String question, String answer)
	{
		JSONObject qaObject = new JSONObject();
		qaObject.put("question", question);
		qaObject.put("answer", answer);
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_414);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());		
		message.setContent(qaObject.toJSONString());
		message.setReceiver(member.getId());
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}

	
	static public void sendListenedSentence(List<Member> memberList, String content)
	{
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_415);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(content);
		message.setReceiver(getMemberIds(memberList));
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}

	static private String getMemberIds(List<Member> memberList)
	{
		String memberIds = "";
		for(Member member : memberList)
		{
			memberIds += "," + member.getId();
		}
		if(!memberIds.isEmpty())
			memberIds = memberIds.substring(1);
		return memberIds;
	}
}
