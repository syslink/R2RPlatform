 
package com.pirobot.client.global;

import java.util.HashMap;
import com.pirobot.client.model.ScriptModel;
import com.pirobot.client.tools.ConfigManager;

public class Global {
	 public static String robotListening = "";
	 public static String robotSpeaking = "";
	 public static String robotSinging = "";
	 public static String robotRepeatLeader = "";
	 public static String scriptMode = "";
	 public static String robotState = "";
	 public static String robotLocation = "";
	 public static String robotName = "";
	 public static String robotSN = "";
	 public static String currentSpeaker = "";
	 public static String currentUid = "";
	 private static boolean debugMode = false;
	 public static final String EN_TEST_RIGHT_TXT = "本次得分：{0}分，很棒";
	 public static final String EN_TEST_FAILED_TXT = "本次得分只有：{0}分，继续加油吧";
	 public static final String EN_TEST_ERROR_TXT = "本次得分只有：{0}分，请再说一遍：";
	 public static String rootDir = "";

	 private static ScriptModel model;
	 private static HashMap<String,Long> handledMessageMap = new HashMap<String,Long>();
	 
	 
	 public static ScriptModel getScriptModel(){
		return model;
	 }
	 
	 public static void addHandledMessage(String mid,long t){
		 handledMessageMap.put(mid, t);
	 }
	 
	 public static boolean hasHandledMessage(String mid){
		 return handledMessageMap.containsKey(mid);
	 }
	 
	 public static void switchScriptModel(ScriptModel obj){
		 model = obj;
		 model.cloneCommandList();
		 scriptMode = Constants.RobotState.STATE_RUNING_SCRIPT;

		 //回复正常模式
	 }
	 
	public static boolean isQuietMode(){
	    	String mode = robotState;
	    	return Constants.RobotState.STATE_QUIET.equals(mode);
	}
	 
	 public static boolean isScriptMode(){
		 return Constants.RobotState.STATE_RUNING_SCRIPT.equals(scriptMode);
	 }

	 public static boolean isSpeaking(){
		 return Constants.RobotState.STATE_SPEAKING.equals(robotSpeaking);
	 }
	 
	 public static void switchSpeakingMode(boolean speaking){
		 if(speaking){
			 robotSpeaking = Constants.RobotState.STATE_SPEAKING;
		 }else{
			 robotSpeaking = "";
		 }
	 }


	 public static boolean isSinging(){
		 return Constants.RobotState.STATE_SINGING.equals(robotSinging);
	 }
	 
	 public static void switchSingingMode(boolean singing){
		 if(singing){
			 robotSinging = Constants.RobotState.STATE_SPEAKING;
		 }else{
			 robotSinging = "";
		 }
	 }
	 
	 public static boolean isRepeatingLeader(){
		 return Constants.RobotState.STATE_REPEATING_LEADER.equals(robotRepeatLeader);
	 }
	 
	 public static void switchRepeatingLeader(boolean singing){
		 if(singing){
			 robotRepeatLeader = Constants.RobotState.STATE_REPEATING_LEADER;
		 }else{
			 robotRepeatLeader = "";
		 }
	 }
	 
	 public static boolean isListening(){
		 return Constants.RobotState.STATE_LISTENING.equals(robotListening);
	 }
	 
	 public static void switchListenMode(boolean listening){
		 if(listening){
			 robotListening = Constants.RobotState.STATE_LISTENING;
		 }else{
			 robotListening = "";
		 }
	 }
	 
	 public static void changeActiveMode(){
		 robotState = Constants.RobotState.STATE_ACTIVE;
	 }
	 
	 public static void changeQuietMode(){
		 robotState = Constants.RobotState.STATE_QUIET;
	 }
	 
	 public static void switchNormalMode(){
		 robotSpeaking = "";
		 scriptMode = "";
		 robotState = Constants.RobotState.STATE_ACTIVE;
	 }
	 
	 public static String getInternalConfig(String key){
		 return ConfigManager.getInstance().getStringValue(key);
	 }
	 
	 public static void setCurrentUID(String uid){
		 currentUid = uid;
	 }
	 
	 public static String getCurrentUID(){
		 return currentUid;
				 
	 }
	 
	 public static void setCurrentSpeaker(String speaker){
		 currentSpeaker = speaker;
	 }
	 
	 public static String getCurrentSpeaker(){
		 if(currentSpeaker == null){
			 return Constants.DEF_SPEAKER;
		 }
		 return currentSpeaker;
	 }
	 
	 public static boolean isDebugMode(){
		return  debugMode;
	 }
	 
	 public static void changeDebugMode(boolean value){
		 debugMode = value;
	 }
}
