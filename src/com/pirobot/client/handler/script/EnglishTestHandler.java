package com.pirobot.client.handler.script;

import java.text.MessageFormat;
import java.util.List;



import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.StringUtils;

public class  EnglishTestHandler implements ScriptCommandHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(EnglishTestHandler.class);
	public boolean handle(ScriptCommand command){
		
		 String question = command.getString(Constants.Action.TEXT);

		 String speaker = command.getString(Constants.Action.SPEAKER);
		 String enspeaker = command.getString(Constants.Action.ENSPEAKER);

		 
		 List<String> sentenceList = StringUtils.getEnglishSentence(question);
		 String criterion = "";
		 for(String sentence:sentenceList){
			 if(StringUtils.isEnglish(sentence)){
				 RobotWrapper.getInstance().getRobotActionIntegration().speak(enspeaker, sentence, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
				 criterion = sentence;
			 }else{
				 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, sentence, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
			 }
		 }
	 
		 //得到用户的 回答
		 String answer = RobotWrapper.getInstance().getRobotActionIntegration().getListenedSentence(false);
		 logger.info("听写结果："+answer + "  答案：" + criterion);
		 double  score = StringUtils.compareEnglishValue(criterion, answer);
		
		 if(score>=Constants.ENGLISH_EQUAL_DEGREE){ //回答正确
			 String text= MessageFormat.format(Global.EN_TEST_RIGHT_TXT, StringUtils.format((int)(score * 100)));
			 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, text, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
		 }else
		 {
			 //，否则给予提示重新回答
			 String text = MessageFormat.format(Global.EN_TEST_ERROR_TXT, StringUtils.format((int)(score * 100)));
			 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, text, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
			 RobotWrapper.getInstance().getRobotActionIntegration().speak(enspeaker, criterion, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);			

			 answer = RobotWrapper.getInstance().getRobotActionIntegration().getListenedSentence(false);
			 logger.info("听写结果："+answer + "  答案：" + criterion);
			 score = StringUtils.compareEnglishValue(criterion, answer);
			 if(score>=Constants.ENGLISH_EQUAL_DEGREE){ //回答正确

				 text = MessageFormat.format(Global.EN_TEST_RIGHT_TXT, StringUtils.format((int)(score * 100)));
				 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, text, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
			 }else
			 {
				 text = MessageFormat.format(Global.EN_TEST_FAILED_TXT, StringUtils.format((int)(score * 100)));
				 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, text, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);		 
			 }
		 }
		
		return true;
	}
	 
}
