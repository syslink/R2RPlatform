package com.pirobot.client.handler.message;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;


import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.handler.script.CommandHandlerFactory;
import com.pirobot.client.handler.script.ScriptUtils;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.network.MessageSendProcessor;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.UDPBordcastAppender;
import com.pirobot.client.tools.UUIDTools;

public class ScriptCommandMessageHandler   implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(ScriptCommandMessageHandler.class);
	private static ExecutorService  executor   = Executors.newCachedThreadPool();

	public synchronized boolean process(Message message){
		
		logger.info(message.getContent());
		//该消息已经通过局域网广播接收过将不再处理
		if(Global.hasHandledMessage(message.getMid())){
			return false;
		}
		

        if(!Global.isScriptMode()){
        	return true;
        }
		
        RobotWrapper.getInstance().getRobotActionIntegration().stopListening();
        
		int sort = Integer.parseInt(message.getContent());

		executeNextCommand(Global.getScriptModel().getCommand(sort));
		
		Global.addHandledMessage(message.getMid(), message.getTimestamp());

		return true;
	}
	
	
	public void processSelf(long parent){
		

		if(Global.getScriptModel().isPaused()){
			logger.info("暂停剧本，当前序列号:"+parent);
			UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】暂停剧本，当前序列号:"+parent);
			return;
		}
		
		List<ScriptCommand> commandList = Global.getScriptModel().getSelfCommandList(parent);
		
		for(final ScriptCommand command:commandList){
			executor.execute(new Runnable() {
				public void run() {
					process(command);
				}
			});
		}
	}
	
	public boolean process(ScriptCommand command){
		
		
		Global.getScriptModel().runing();

		
		Global.getScriptModel().setCurrent(command);
		
		logger.info("开始执行剧本:"+command.getSort());
		UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK 
												  + "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() 
												  + "】开始执行剧本:"+command.getSort() + "【内容】" + command.getContent());
		
		CommandHandlerFactory.getFactory().handle(command);
		
		
		executeNextCommand(command);
		
		Global.getScriptModel().runend();
		return true;
	}
	

	public void executeNextCommand(ScriptCommand current) {
		
		if(current == null)
		{
			logger.warn("警告：出现不存在的剧本");
			UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "警告：出现不存在的剧本");
			return;
		}
		// 移除克隆的剧情（已经表演过了）
		Global.getScriptModel().removeCloned(current);

		// 如果此剧情（已经表演过了）是自己执行的，则将消息发送给其它表演者
		if(Global.getScriptModel().isSelf(current))
		{
	        notifyNextRole(current.getSort());
		}
		// 如果剧本都已经执行完，则退出本次表演，机器人恢复常态
		if(Global.getScriptModel().isLastCommand()){
			
			logger.info("剧本执行完毕:"+Global.getScriptModel().getTitle() + " " + current.getSort());
			UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】剧本执行完毕:"+Global.getScriptModel().getTitle() + " " + current.getSort());
			
			ScriptUtils.onScriptExecuteDone();
			
		}else
		{
			processSelf(current.getSort());
		}
		
		
	}
	
	 
	public void notifyNextRole(long sort){
		
		if(Global.getScriptModel().isPaused()){
			logger.info("暂停剧本，当前序列号:" + sort);
			UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】暂停剧本，当前序列号:" + sort);

			return;
		}
		
		logger.info("通知下一个角色执行剧本，当前序列号:"+sort);
		UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】通知下一个角色执行剧本，当前序列号:" + sort);
		
		
		List<String> receiverList=  Global.getScriptModel().getOtherDeviceIds();
		
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_901);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(String.valueOf(sort));
		message.setReceiver(StringUtils.join(receiverList.toArray(),","));
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		//UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
		MessageSendProcessor.send(message);
	}
	
	public static void shutdownAllTask()
	{
		UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】关闭所有先前的剧本线程");
		executor.shutdownNow();
		executor = Executors.newCachedThreadPool();
		CommandHandlerFactory.getFactory().shutdownAllTask();
	}

}
