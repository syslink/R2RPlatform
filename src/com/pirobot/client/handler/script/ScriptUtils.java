package com.pirobot.client.handler.script;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.network.MessageSendProcessor;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.CommonUtils;
import com.pirobot.client.tools.ThreadUtils;
import com.pirobot.client.tools.UDPBordcastAppender;
import com.pirobot.client.tools.UUIDTools;

public class ScriptUtils {
	private static ConcurrentHashMap<String, String> hasReadyRolesMap = new ConcurrentHashMap<String, String>();
	public static void onScriptExecuteDone(){
		CommonUtils.killMusic();
		Global.switchNormalMode();
		Global.changeQuietMode();
		Global.switchRepeatingLeader(false);
		Global.getScriptModel().clearCloned();
		RobotWrapper.getInstance().getRobotActionIntegration().notifyScriptStop();
	}
	public static void waitOtherRolesReady(String scriptId, List<String> roleIds)
	{
		while(true)
		{
			int readyRoleNum = 0;
			for(String roleId : roleIds)
			{
				if(hasReadyRolesMap.containsKey(roleId) && scriptId.equals(hasReadyRolesMap.get(roleId)))
				{
					readyRoleNum++;
				}
			}
			if(readyRoleNum == roleIds.size())
			{
				hasReadyRolesMap.clear();
				break;
			}
			ThreadUtils.sleep(200);
		}
	}
	public static void sendReadyMsgToLeader(String leaderId, String scriptId)
	{
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_904);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(scriptId);
		message.setReceiver(leaderId);
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}
	public static void processRoleReadyMsg(String roleId, String scriptId)
	{
		hasReadyRolesMap.put(roleId, scriptId);
		UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + 
				 "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】收到成员准备好的消息：" + roleId);
	}
}
