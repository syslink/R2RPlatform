package com.pirobot.client.handler.script;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;


import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.ThreadUtils;

public class  SpeakHandler implements ScriptCommandHandler {
	private ExecutorService executor = Executors.newCachedThreadPool();
	public boolean handle(ScriptCommand command){
		String speaker = command.getString(Constants.Action.SPEAKER);
		String text = command.getString(Constants.Action.TEXT);

		int volume = command.getIntValue(Constants.Action.VOLUME,Constants.DEF_TTS_VALUE);
		int speed = command.getIntValue(Constants.Action.SPEED,Constants.DEF_TTS_VALUE);
		int tone =command.getIntValue(Constants.Action.TONE,Constants.DEF_TTS_VALUE);			
		
		final int predictTime = (int)((50 * 1.0 / speed) * (text.length() / 3.0)) * 1000; //ms

		final int emotionDelayTime = command.getIntValue(Constants.Action.EMOTION_DELAY_TIME);
		final String emotion = command.getString(Constants.Action.EMOTION);
		final String headTurnListInfo = command.getString(Constants.Action.HEAD_TURN_LIST);
		final String moveListInfo = command.getString(Constants.Action.MOVE_LIST);
		final String rightAngleInfo = command.getString(Constants.Action.RIGHT_ANGEL);
		final String leftAngleInfo = command.getString(Constants.Action.LEFT_ANGEL);
		final int combineActionsDelayTime = command.getIntValue(Constants.Action.COMBINEACTIONS_DELAY_TIME);
		final String combineActionsName = command.getString(Constants.Action.COMBINEACTIONS);
		final String ledControllerInfo = command.getString(Constants.Action.LEDCONTROLLER);
		final String motorControllerInfo = command.getString(Constants.Action.MOTORCONTROLLER);

		if(!StringUtils.isEmpty(emotion) && !emotion.equals("none"))
			executor.execute(new Runnable(){public void run() {
				if(emotionDelayTime > 0)
					ThreadUtils.sleep(emotionDelayTime);
				RobotWrapper.getInstance().showEmotion(emotion, predictTime - emotionDelayTime);
			}
			});
		
		if(!StringUtils.isEmpty(ledControllerInfo) && !ledControllerInfo.equals("0"))
			executor.execute(new Runnable(){public void run() {
				RobotWrapper.getInstance().controlLed(ledControllerInfo);
			}
		});
		
		
		if(!StringUtils.isEmpty(combineActionsName) && !combineActionsName.equals("none"))
		{
			executor.execute(new Runnable(){public void run() {
				if(combineActionsDelayTime > 0)
					ThreadUtils.sleep(combineActionsDelayTime);
				RobotWrapper.getInstance().excuteCombineActions(combineActionsName, "");
			}
			});
		}
		else{
			if(!StringUtils.isEmpty(headTurnListInfo) && !headTurnListInfo.equals("0"))
				executor.execute(new Runnable(){public void run() {
					RobotWrapper.getInstance().exeHeadTurnList(headTurnListInfo);
					}
				});
			if(!StringUtils.isEmpty(moveListInfo) && !moveListInfo.equals("0"))
				executor.execute(new Runnable(){public void run() {
					RobotWrapper.getInstance().exeMoveList(moveListInfo);
					}
				});
			if(!StringUtils.isEmpty(leftAngleInfo) && !leftAngleInfo.equals("0"))
			{
				executor.execute(new Runnable(){public void run() {
					RobotWrapper.getInstance().turnHand(leftAngleInfo, false);
					}
				});
			}
			if(!StringUtils.isEmpty(rightAngleInfo) && !rightAngleInfo.equals("0"))
				executor.execute(new Runnable(){public void run() {
					RobotWrapper.getInstance().turnHand(rightAngleInfo, true);
					}
				});
			if(!StringUtils.isEmpty(motorControllerInfo) && !motorControllerInfo.equals("0"))
				executor.execute(new Runnable(){public void run() {
					RobotWrapper.getInstance().controlMotor(motorControllerInfo);
				}
			});
		}

//		if(emotion != null)
//			RobotWrapper.getInstance().showEmotion(emotion, predictTime - emotionDelayTime);
		RobotWrapper.getInstance().getRobotActionIntegration().speak(speaker, text, speed, volume, tone);
		
		
		return true;
	}
	 
	 
}
