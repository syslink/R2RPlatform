package com.pirobot.client.handler.script;



import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.network.SamenticCompareProcessor;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.StringUtils;

public class  QAHandler implements ScriptCommandHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(QAHandler.class);
	public boolean handle(ScriptCommand command){
		
		 
		 String question = command.getString(Constants.Action.QUESTION);
		 String criterion = command.getString(Constants.Action.CRITERION);
		 String errorhint = command.getString(Constants.Action.ERRORHINT);
		 
		 int count =command.getIntValue(Constants.Action.COUNT,1);
		 String failtips = command.getString(Constants.Action.FAILTIPS);
		 String righttips = command.getString(Constants.Action.RIGHTTIPS);
		 String speaker = command.getString(Constants.Action.SPEAKER);
		 
		 //机器人提出问题
		 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, question, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
		 
		 boolean isEnglish = StringUtils.isEnglish(criterion);
		 for(int i = 1; i <= count; i++){
			 //得到用户的 回答
			 String answer = RobotWrapper.getInstance().getRobotActionIntegration().getListenedSentence(!isEnglish);
			 boolean isRight = SamenticCompareProcessor.compare(criterion, answer);
			
			 if(isRight){ //回答正确
				 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, righttips, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);
				 break;
			 }else
			 {
				 //到达最大回答次数提示回答失败，否则给予提示重新回答
				 RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, i == count ? failtips : errorhint, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE, Constants.DEF_TTS_VALUE);				 
			 }
			 
		 }
		return true;
	}
	 
}
