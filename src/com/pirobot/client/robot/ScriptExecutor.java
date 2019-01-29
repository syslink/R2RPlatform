package com.pirobot.client.robot;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.handler.message.MessageHandlerFactory;
import com.pirobot.client.handler.message.ScriptCommandMessageHandler;
import com.pirobot.client.handler.script.ScriptUtils;
import com.pirobot.client.model.Member;
import com.pirobot.client.model.Script;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.model.ScriptModel;
import com.pirobot.client.network.MessageSendProcessor;
import com.pirobot.client.network.ScriptDetailedProcessor;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.UUIDTools;

public class ScriptExecutor {
	public static boolean process(Script script){	   
		 if(script==null){
			 return false;
		 }
		 // 如果剧本要求的机器人数量比现场的机器人数量多，则无法表演
		 if(script.getRoleCount() > TeamMemberManager.getInstance().getAllMemberNum())
			 return false;
		 ScriptModel model = new ScriptModel();
		 model.script = script;
		 ScriptCommand scriptCommand = model.getFristCommand();
		 model.leader = scriptCommand.getRole();
		 model.setSelfRole(model.leader); 
		 model.roles.put(model.leader, RobotWrapper.getInstance().getDeviceId());		 
		 
		 int restRoleNum = script.getRoleCount() - 1;
		 List<String> otherRoles = model.getOtherRole();
		 List<Member> otherMemberList = TeamMemberManager.getInstance().getOtherMemberList();
		 for(int i = 0; i < restRoleNum; i++)
		 {
			 Member anotherMember = otherMemberList.get(i);
			 String role = otherRoles.get(i);
			 model.roles.put(role, anotherMember.getId());			 
		 }
		 for(int i = 0; i < restRoleNum; i++)
		 {
			 ScriptModel copiedModel = model.copy();
			 Member anotherMember = otherMemberList.get(i);
			 String role = otherRoles.get(i);
			 copiedModel.setSelfRole(role);

			 Message message = new Message();
			 message.setAction(Constants.MessageAction.ACTION_900);
			 message.setFormat(Constants.MessageFromat.FROMAT_TXT);
			 message.setMid(UUIDTools.randomUUID());
			 message.setContent(JSON.toJSONString(model));
			 message.setReceiver(anotherMember.getId());
			 message.setSender(RobotWrapper.getInstance().getDeviceId());
			 UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
			 MessageSendProcessor.send(message);
		 }
		 
		 Message message = new Message();
		 message.setAction(Constants.MessageAction.ACTION_900);
		 message.setContent(JSON.toJSONString(model));
		 MessageHandlerFactory.getFactory().handle(message);
		 
		 return true;
	}
	public static boolean runScript(String sid)
	{
		Script script = ScriptDetailedProcessor.get(sid);
		return process(script);
	}
	public static boolean hasScript(String sid)
	{
		return ScriptDetailedProcessor.get(sid) != null;
	}
	public static boolean randomRunScriptByType(String type)
	{
		Script script = ScriptDetailedProcessor.getRandom(RobotWrapper.getInstance().getUid(), type);
		return process(script);
	}
	public static void pause()
	{
		boolean isScriptMode = Global.isScriptMode();
		if(isScriptMode){
			Global.getScriptModel().pause();

			if(!Global.getScriptModel().isRuning()){
				Global.getScriptModel().setCurrent(null);
			}
		}
	}
	public static void stop()
	{
		boolean isScriptMode = Global.isScriptMode();
		if(isScriptMode){
			ScriptUtils.onScriptExecuteDone();
		}
	}
	public static void resume()
	{
		Global.getScriptModel().resume();
		ScriptCommand current = Global.getScriptModel().getCurrent();
		if(current!=null){
			ScriptCommandMessageHandler messageHandler = ((ScriptCommandMessageHandler)MessageHandlerFactory.getFactory().getMessageHandler(Constants.MessageAction.ACTION_901));
			messageHandler.executeNextCommand(current);
		}
	}
	public static ScriptStatus getScriptStatus()
	{
		ScriptStatus status = ScriptStatus.STOP;
		if(Global.getScriptModel().isRuning())
			status = ScriptStatus.RUNNING;
		if(Global.getScriptModel().isPaused())
			status = ScriptStatus.PAUSE;
		return status;
	}
	public static boolean isScriptMode()
	{
		return Global.isScriptMode();
	}
}
