package com.pirobot.client.handler.script;

import java.util.ArrayList;
import java.util.List;



import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.StringUtils;

import com.pirobot.client.tools.Pair;

public class  BlendSpeakHandler implements ScriptCommandHandler {
	public boolean handle(ScriptCommand command){
		
		 String question = command.getString(Constants.Action.TEXT);

		 String speaker = command.getString(Constants.Action.SPEAKER);
		 String enspeaker = command.getString(Constants.Action.ENSPEAKER);

		 int volume = command.getIntValue(Constants.Action.VOLUME,Constants.DEF_TTS_VALUE);
		 int speed = command.getIntValue(Constants.Action.SPEED,Constants.DEF_TTS_VALUE);
		 int tone =command.getIntValue(Constants.Action.TONE,Constants.DEF_TTS_VALUE);	
		 
		 List<String> sentenceList = StringUtils.getEnglishSentence(question);
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
	 
		RobotWrapper.getInstance().getRobotActionIntegration().blendSpeak(speaker, enspeaker, taggedSentenceList, speed, volume, tone);
		return true;
	}
 
}
