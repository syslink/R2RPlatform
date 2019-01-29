package com.pirobot.client.handler.message;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.handler.script.ScriptUtils;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptModel;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.ThreadUtils;
import com.pirobot.client.tools.UDPBordcastAppender;

public class ScriptMessageHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(ScriptMessageHandler.class);
	private static ExecutorService  executor   = Executors.newCachedThreadPool();


	public boolean process(Message message){
		
		ScriptModel model = JSON.parseObject(message.getContent(), ScriptModel.class);
		Global.switchScriptModel(model);
		Global.changeActiveMode();
		UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + 
												 "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】收到剧本:"
												 + model.script.getCommandList());
		/**
		 * 收到剧本创建小组
		 */
        boolean  isSelfFrist = model.isSelfFrist();
        if(isSelfFrist)
        	model.leader = model.getSelfRole();
        
//        UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + 
//				 "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】"
//				 + "开始复原各个状态");
        
        //RobotWrapper.getInstance().hand("0", "0");
//		RobotWrapper.getInstance().getRobotActionIntegration().stopListening();
//		RobotWrapper.getInstance().getRobotActionIntegration().stopSpeak();
//		RobotWrapper.getInstance().getRobotActionIntegration().reset();
//        ThreadUtils.sleep(2000);
		//beforehandTTS(model);
		
		UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + 
				 "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】"
				 + "开始关闭所有先前的剧本任务");
		ScriptCommandMessageHandler.shutdownAllTask();
        // ScriptCommand fristCommand = model.getFristCommand();
		if(isSelfFrist){
			ScriptUtils.waitOtherRolesReady(model.script.getSid(), model.getOtherDeviceIds());
			ScriptCommandMessageHandler commandMessageHandler =	(ScriptCommandMessageHandler)MessageHandlerFactory.getFactory().getMessageHandler(Constants.MessageAction.ACTION_901);
			commandMessageHandler.processSelf(0);
		}
		else
		{
			UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + 
					 "剧本【" + Thread.currentThread().getId() + "："  + RobotWrapper.getInstance().getName() + "】向队长发送剧本准备好消息:"
					 + model.script.getSid());
			ScriptUtils.sendReadyMsgToLeader(model.getLeaderId(), model.script.getSid());
		}
		
		return true;
	}
	
	//在剧本刚下发之时，即预合成所有TTS
	private void beforehandTTS(ScriptModel model ){

		executor.execute(new Runnable() {
			
			@Override
			public void run() {
//				List<ScriptCommand> selfList = model.getSelfCommandList(Constants.ScriptAction.ACTION_1);
				
//				for(ScriptCommand command:selfList){
//					String speaker = command.getString(Constants.Action.SPEAKER);
//					String text = command.getString(Constants.Action.TEXT);
//					int volume = command.getIntValue(Constants.Action.VOLUME,Constants.DEF_TTS_VALUE);
//					int speed = command.getIntValue(Constants.Action.SPEED,Constants.DEF_TTS_VALUE);
//					int tone =command.getIntValue(Constants.Action.TONE,Constants.DEF_TTS_VALUE);
					
					// TODO:
					//XunfeiJniBridge.getInstance().ttsQuietly(speaker,text,speed,volume,tone);
//				}
			}
		});
	}
	
	private void savaScriptToFile(String scriptId, String scriptContent)
	{
		try{
			File scriptFile = new File(Constants.CacheDir.script, scriptId + ".script");
			scriptFile.createNewFile();
			FileOutputStream o = new FileOutputStream(scriptFile);  
		    o.write(scriptContent.getBytes("GBK"));  
		    o.close();
		}catch(Exception e){}
	}
	
	private String getScriptContent(String scriptId)
	{
		String scriptContent = "";
		try{
			File scriptFile = new File(Constants.CacheDir.script, scriptId + ".script");
			if(scriptFile.exists())
			{
				FileReader fileReader = new FileReader(scriptFile);
				BufferedReader bufferedReader =new BufferedReader(fileReader);  
				String read = null;  
			    while((read = bufferedReader.readLine())!=null){  
			    	scriptContent = scriptContent + read + "\r\n";  
			    } 
			    bufferedReader.close(); 
			}
		}catch(Exception e){}
		return scriptContent;
	}
}
