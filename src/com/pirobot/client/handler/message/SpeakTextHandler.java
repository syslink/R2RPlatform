package com.pirobot.client.handler.message;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;

public class SpeakTextHandler implements CIMMessageHandler {

	public boolean process(Message message){		
		Global.changeActiveMode();
		try{
			JSONObject contentObj = JSONObject.parseObject(message.getContent());
			String speaker = contentObj.getString(Constants.Action.SPEAKER);
			String text = contentObj.getString(Constants.Action.TEXT);
			Integer speed = contentObj.getInteger(Constants.Action.SPEED);
			Integer volume = contentObj.getInteger(Constants.Action.VOLUME);
			Integer tone = contentObj.getInteger(Constants.Action.TONE);
			if(!StringUtils.isEmpty(text))
				RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker != null ? speaker : RobotWrapper.getInstance().getDefaultChSpeakerName(), 
																		     text, 
																		     speed != null? speed : Constants.DEF_TTS_VALUE, 
																		     volume != null? volume : Constants.DEF_TTS_VALUE, 
																		     tone != null? tone : Constants.DEF_TTS_VALUE);
		}catch(Exception e)
		{
			RobotWrapper.getInstance().getRobotActionIntegration().speak(RobotWrapper.getInstance().getDefaultChSpeakerName(), message.getContent(), 
					Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
		}
		
		return true;
	}
 
}
