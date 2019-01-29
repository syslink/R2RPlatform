package com.pirobot.client.handler.message;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.Pair;
import com.pirobot.client.tools.StringUtils;

public class BlendSpeakHandler implements CIMMessageHandler {

	public boolean process(Message message){
		
		JSONObject contentObj = JSONObject.parseObject(message.getContent());

		 String content = contentObj.getString(Constants.Action.TEXT);

		 String speaker = contentObj.getString(Constants.Action.SPEAKER);
		 String enspeaker = contentObj.getString(Constants.Action.ENSPEAKER);

		 Integer speed = contentObj.getInteger(Constants.Action.SPEED);
		 Integer volume = contentObj.getInteger(Constants.Action.VOLUME);
		 Integer tone = contentObj.getInteger(Constants.Action.TONE);
		 
		 List<String> sentenceList = StringUtils.getEnglishSentence(content);
		 List<Pair<String, Boolean>> taggedSentenceList = new ArrayList<Pair<String, Boolean>>();
		 for(String sentence:sentenceList){
			 if(StringUtils.isEnglish(sentence)){
			 {
				 taggedSentenceList.add(new Pair<String, Boolean>(sentence, false)); 
			 }
			 }else{
				 taggedSentenceList.add(new Pair<String, Boolean>(sentence, false)); 
			 }
		 }
	 
		RobotWrapper.getInstance().getRobotActionIntegration().blendSpeak(speaker != null ? speaker : Constants.DEF_SPEAKER, 
																		  enspeaker != null ? enspeaker : Constants.DEF_ENGLISH_SPEAKER, 
																		  taggedSentenceList, 
																		  speed != null ? speed : Constants.DEF_TTS_VALUE, 
																	      volume != null ? volume : Constants.DEF_TTS_VALUE, 
																	      tone != null ? tone : Constants.DEF_TTS_VALUE);
		
		return true;
	}
 
}
