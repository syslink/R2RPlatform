package com.pirobot.client.handler.message;


import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.handler.script.ScriptUtils;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.LoggerUtils;

public class ScriptHoldHandler implements CIMMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(ScriptHoldHandler.class);


	public boolean process(Message message){
		
		String action = message.getContent();
		boolean isScriptMode = Global.isScriptMode();
		logger.info("isScriptMode:"+isScriptMode);
		 
		if(Constants.STATE_PAUSE.equals(action) && isScriptMode){
			Global.getScriptModel().pause();
			logger.info("ScriptModel.isRuning:"+Global.getScriptModel().isRuning());

			if(!Global.getScriptModel().isRuning()){
				Global.getScriptModel().setCurrent(null);
			}
		}
		
		if(Constants.STATE_CANCEL.equals(action) && isScriptMode){

			ScriptUtils.onScriptExecuteDone();
		}
		
		if(Constants.STATE_RESUME.equals(action)){
			Global.getScriptModel().resume();
			ScriptCommand current = Global.getScriptModel().getCurrent();
			logger.info("ScriptModel.currentSort:"+current);
			if(current!=null){
				ScriptCommandMessageHandler messageHandler = ((ScriptCommandMessageHandler)MessageHandlerFactory.getFactory().getMessageHandler(Constants.MessageAction.ACTION_901));
				messageHandler.executeNextCommand(current);
			}
		}
		
		return true;
	}
	
	
}
