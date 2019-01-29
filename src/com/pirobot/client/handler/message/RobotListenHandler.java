package com.pirobot.client.handler.message;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;

public class RobotListenHandler implements CIMMessageHandler {

	public boolean process(Message message){		
		if(Global.isListening())
			RobotWrapper.getInstance().getRobotActionIntegration().stopListening();
		Global.switchListenMode(true);
		try{
			JSONObject contentObj = JSONObject.parseObject(message.getContent());
			String listenedSentenceId = contentObj.getString(Constants.Action.LISTENED_SENTENCE_ID);
			boolean isToListenChinese =  Boolean.parseBoolean(contentObj.getString(Constants.Action.IS_TO_LISTEN_CHINESE));
			
			String listenedSentence = RobotWrapper.getInstance().getRobotActionIntegration().getListenedSentence(isToListenChinese);
			
		}
		catch(Exception e)
		{
			RobotWrapper.getInstance().getRobotActionIntegration().speak(RobotWrapper.getInstance().getDefaultChSpeakerName(), message.getContent(), 
					Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
		}
		
		return true;
	}
 
}
