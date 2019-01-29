package com.pirobot.client.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.pirobot.client.tools.Pair;
import com.pirobot.client.tools.ThreadUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.client.driver.Protocol;
import com.pirobot.client.model.Member;
import com.pirobot.client.robot.*;
import com.pirobot.client.team.AbilityProxyManager;

public class RobotActionIntegration {
	private ArmInterface armInterface = null;
	private BrainInterface brainInterface = null;
	private DeviceManageInterface deviceManageInterface = null;
	private EarInterface earInterface = null;
	private EmotionInterface emotionInterface = null;
	private CombineActionsInterface combineActionsInterface = null;
	private EyeInterface eyeInterface = null;
	private HeadInterface headInterface = null;
	private LegInterface legInterface = null;
	private MouthInterface mouthInterface = null;
	private ScriptInterface scriptInterface = null;
	private SerialPortInterface serialPortInterface = null;
	private InteractionInterface interactionInterface = null;
	private TeamMemberInterface teamMemberInterface = null;
	private LedControllerInterface ledControllerInterface = null;
	private MotorControllerInterface motorControllerInterface = null;
	private LoggerInterface loggerInterface = null;
	
	public void setArmInterface(ArmInterface armInterface) {
		this.armInterface = armInterface;
	}


	public void setBrainInterface(BrainInterface brainInterface) {
		this.brainInterface = brainInterface;
	}


	public void setDeviceManageInterface(DeviceManageInterface deviceManageInterface) {
		this.deviceManageInterface = deviceManageInterface;
	}


	public void setEarInterface(EarInterface earInterface) {
		this.earInterface = earInterface;
	}


	public void setEmotionInterface(EmotionInterface emotionInterface) {
		this.emotionInterface = emotionInterface;
	}


	public void setEyeInterface(EyeInterface eyeInterface) {
		this.eyeInterface = eyeInterface;
	}


	public void setCombineActionsInterface(CombineActionsInterface combineActionsInterface) {
		this.combineActionsInterface = combineActionsInterface;
	}


	public void setHeadInterface(HeadInterface headInterface) {
		this.headInterface = headInterface;
	}


	public void setLegInterface(LegInterface legInterface) {
		this.legInterface = legInterface;
	}


	public void setMouthInterface(MouthInterface mouthInterface) {
		this.mouthInterface = mouthInterface;
	}


	public void setScriptInterface(ScriptInterface scriptInterface) {
		this.scriptInterface = scriptInterface;
	}

	public void setSerialPortInterface(SerialPortInterface serialPortInterface) {
		this.serialPortInterface = serialPortInterface;
	}


	public void setInteractionInterface(InteractionInterface interactionInterface) {
		this.interactionInterface = interactionInterface;
	}

	public void setTeamMemberInterface(TeamMemberInterface teamMemberInterface) {
		this.teamMemberInterface = teamMemberInterface;
	}

	public void setLedControllerInterface(LedControllerInterface ledControllerInterface) {
		this.ledControllerInterface = ledControllerInterface;
	}

	public void setMotorControllerInterface(MotorControllerInterface motorControllerInterface) {
		this.motorControllerInterface = motorControllerInterface;
	}

	public void setLoggerInterface(LoggerInterface loggerInterface) {
		this.loggerInterface = loggerInterface;
	}

	public boolean speak(String content)
	{
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Mouth);
		if(proxyRobotSet.size() > 0)
		{
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_303, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content);
			return true;
		}
		if(mouthInterface == null)
			return false;
		try{
			return mouthInterface.speak(content);
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean speak(String speakerName, String content, int speed, int volume, int tone) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Mouth);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject contentObj = new JSONObject();
			contentObj.put(Constants.Action.SPEAKER, speakerName);
			contentObj.put(Constants.Action.TEXT, content);
			contentObj.put(Constants.Action.SPEED, speed);
			contentObj.put(Constants.Action.VOLUME, volume);
			contentObj.put(Constants.Action.TONE, tone);
			
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_303, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  contentObj.toJSONString());
			return true;
		}
		if(mouthInterface == null)
			return false;
		try{
			return mouthInterface.speak(speakerName, content, speed, volume, tone);
		}catch(Exception e){
			return false;
		}
	}

	public boolean blendSpeak(String chSpeakerName, String enSpeakerName, List<Pair<String, Boolean>> sentenceList, int speed, int volume, int tone)
	{
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Mouth);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject contentObj = new JSONObject();
			contentObj.put(Constants.Action.SPEAKER, chSpeakerName);
			contentObj.put(Constants.Action.ENSPEAKER, enSpeakerName);
			String content = "";
			for(Pair<String, Boolean> pair : sentenceList)
			{
				content += pair.getKey();
			}
			contentObj.put(Constants.Action.TEXT, content);
			contentObj.put(Constants.Action.SPEED, speed);
			contentObj.put(Constants.Action.VOLUME, volume);
			contentObj.put(Constants.Action.TONE, tone);
			
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_304,
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  contentObj.toJSONString());
			return true;
		}
		if(mouthInterface == null)
			return false;
		try{
			return mouthInterface.blendSpeak(chSpeakerName, enSpeakerName, sentenceList, speed, volume, tone);
		}catch(Exception e){
			return false;
		}
	}
	public boolean blendSpeak(List<Pair<String, Boolean>> sentenceList)
	{
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Mouth);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject contentObj = new JSONObject();
			String content = "";
			for(Pair<String, Boolean> pair : sentenceList)
			{
				content += pair.getKey();
			}
			contentObj.put(Constants.Action.TEXT, content);
			
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_304,
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  contentObj.toJSONString());
			return true;
		}
		if(mouthInterface == null)
			return false;
		try{
			return mouthInterface.blendSpeak(sentenceList);
		}catch(Exception e){
			return false;
		}
	}

	public void stopSpeak() {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Mouth);
		if(proxyRobotSet.size() > 0)
		{
			
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_305,
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  "");
			return;
		}
		if(mouthInterface == null)
			return;
		try{
			mouthInterface.stopSpeak();
		}catch(Exception e){
			
		}
	}
		
	public boolean play(String filePath) {
		if(mouthInterface == null)
			return false;
		try{
			return mouthInterface.play(filePath);
		}catch(Exception e){
			return false;
		}
	}

	
	public void stopPlay() {
		if(mouthInterface == null)
			return;
		try{
			mouthInterface.stopPlay();
		}catch(Exception e){
		}
	}


	public int turnHead(Orientation orientation, int angle, int duration) 
	{
		if(headInterface == null)
			return 0;
		try{
			return headInterface.turn(orientation, angle, duration);
		}catch(Exception e){
			return 0;
		}
	}
	
	public int move(Orientation orientation, int distanceOrAngle, int duration) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Leg);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("action", Protocol.ACTION_1001);
			content.put("orientation", orientation.toString());
			content.put("distance", distanceOrAngle);
			content.put("duration", duration);
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_700, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return duration;
		}
		if(legInterface == null)
			return 0;
		try{
			return legInterface.move(orientation, distanceOrAngle, duration);
		}catch(Exception e){
			return 0;
		}
	}

	public int moveToPosition(String posName, PosInfo posInfo) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Leg);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("posName", posName);
			content.put("position", posInfo.posX + "," + posInfo.posY + "," + posInfo.a + "," + posInfo.w);
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_702, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return 0;
		}
		if(legInterface == null)
			return 0;
		try{
			return legInterface.moveToPosition(posName, posInfo);
		}catch(Exception e){
			return 0;
		}
	}
	
	public int rotate(Orientation orientation, int radius, int angle, int duration) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Leg);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("action", Protocol.ACTION_1003);
			content.put("orientation", orientation.toString());
			content.put("radius", radius);
			content.put("angle", angle);
			content.put("duration", duration);
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_700, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return duration;
		}
		if(legInterface == null)
			return 0;
		try{
			return legInterface.rotate(orientation, radius, angle, duration);
		}catch(Exception e){
			return 0;
		}
	}

	
	public JSONArray detectObjectPos(JSONObject objectInfo) {
		if(eyeInterface == null)
			return null;
		try{
			return eyeInterface.detectObjectPos(objectInfo);
		}catch(Exception e){
			return null;
		}
	}

	
	public void displayEmotion(String emotionName, int duration) {
		if(emotionInterface == null)
			return;
		try{
			emotionInterface.display(emotionName, duration);
		}catch(Exception e){
		}
	}

	
	public void excuteCombineActions(String combineActionName, String extraContent) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.CombineAction);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("combineActionsName", combineActionName);
			content.put("extraContent", extraContent);
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_703, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return;
		}
		if(combineActionsInterface == null)
			return;
		try{
			combineActionsInterface.excute(combineActionName, extraContent);
		}catch(Exception e){
		}
	}

	
	public void excuteCombineActions(String combineActionName, String extraContent, boolean bMusic) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.CombineAction);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("combineActionsName", combineActionName);
			content.put("extraContent", extraContent);
			content.put("music", bMusic);
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_703, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return;
		}
		if(combineActionsInterface == null)
			return;
		try{
			combineActionsInterface.excute(combineActionName, extraContent, bMusic);
		}catch(Exception e){
		}
	}
	
	public boolean startListening() {
		if(earInterface == null)
			return false;
		try{
			return earInterface.startListening();
		}catch(Exception e){
			return false;
		}
	}

	
	public boolean stopListening() {
		if(earInterface == null)
			return false;
		try{
			return earInterface.stopListening();
		}catch(Exception e){
			return false;
		}
	}

	
	public String getListenedSentence(boolean isListeningChinese) {
		if(earInterface == null)
			return "";
		try{
			return earInterface.getListenedSentence(isListeningChinese);
		}catch(Exception e){
			return "";
		}
	}

	
	public boolean isListening() {
		if(earInterface == null)
			return false;
		try{
			return earInterface.isListening();
		}catch(Exception e){
			return false;
		}
	}

	
	public void reboot() {
		if(deviceManageInterface == null)
			return;
		try{
			deviceManageInterface.reboot();
		}catch(Exception e){
		}
	}

	
	public void shutdown() {
		if(deviceManageInterface == null)
			return;
		try{
			deviceManageInterface.shutdown();
		}catch(Exception e){
		}
	}

	public void reset() {
		if(deviceManageInterface == null)
			return;
		try{
			deviceManageInterface.reset();
		}catch(Exception e){
		}
	}
	
	public void setWifi(String account, String password) {
		if(deviceManageInterface == null)
			return;
		try{
			deviceManageInterface.setWifi(account, password);
		}catch(Exception e){
		}
	}
	
	public void setRobotName(String robotName) {
		if(deviceManageInterface == null)
			return;
		try{
			deviceManageInterface.setRobotName(robotName);
		}catch(Exception e){
		}
	}

	
	public void processText(String text) {
		if(brainInterface == null)
			return;
		try{
			brainInterface.processText(text);
		}catch(Exception e){
		}
	}

	
	public void processMemberQuestion(Member member, String question, List<Member> unknownMemberList) {
		if(brainInterface == null)
			return;
		try{
			brainInterface.processMemberQuestion(member, question, unknownMemberList);
		}catch(Exception e){
		}
	}

	
	public void learnQA(String question, String answer) {
		if(brainInterface == null)
			return;
		try{
			brainInterface.learnQA(question, answer);
		}catch(Exception e){
		}
	}
	
	public int rotate(boolean isRightHand, String handInfo) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Arm);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("action", Protocol.ACTION_1016);
			if(isRightHand)
			{
				content.put("leftangle", "");
				content.put("rightangle", handInfo);
			}
			else
			{
				content.put("rightangle", "");
				content.put("leftangle", handInfo);
			}
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_701, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return 1000;
		}
		if(armInterface == null)
			return 0;
		try{
			return armInterface.rotate(isRightHand, handInfo);
		}catch(Exception e){
			return 0;
		}
	}

	
	public int rotate(String leftHandInfo, String rightHandInfo) {
		Set<String> proxyRobotSet = AbilityProxyManager.getInstance().getProxyRobots(ProxyType.Arm);
		if(proxyRobotSet.size() > 0)
		{
			JSONObject content = new JSONObject();
			content.put("action", Protocol.ACTION_1016);
			content.put("leftangle", leftHandInfo);
			content.put("rightangle", rightHandInfo);
			MemberInteraction.sendMessage(Constants.MessageAction.ACTION_701, 
										  proxyRobotSet.toString().substring(1, proxyRobotSet.toString().length() - 1), 
										  content.toJSONString());
			return 1000;
		}
		if(armInterface == null)
			return 0;
		try{
			return armInterface.rotate(leftHandInfo, rightHandInfo);
		}catch(Exception e){
			return 0;
		}
	}

	public void controlLed(String ledInfo)
	{
		if(ledControllerInterface == null)
			return;
		try{
			ledControllerInterface.excute(ledInfo);
		}catch(Exception e){
		}
	}

	public void controlMotor(String motorInfo)
	{
		if(motorControllerInterface == null)
			return;
		try{
			motorControllerInterface.excute(motorInfo);
		}catch(Exception e){
		}
	}
	
	public void notifyScriptStart() {
		if(scriptInterface == null)
			return;
		try{
			scriptInterface.notifyScriptStart();
		}catch(Exception e){
		}
	}

	
	public void notifyScriptPause() {
		if(scriptInterface == null)
			return;
		try{
			scriptInterface.notifyScriptPause();
		}catch(Exception e){
		}
	}

	
	public void notifyScriptStop() {
		if(scriptInterface == null)
			return;
		try{
			scriptInterface.notifyScriptStop();
		}catch(Exception e){
		}
	}
	
	public void writeToSerialPort(byte[] data)
	{
		if(serialPortInterface == null)
			return;
		try{
			serialPortInterface.write(data);
		}catch(Exception e){
		}
	}
	
	public void sayHelloToMe(Member member, String conent)
	{
		if(interactionInterface == null)
			return;
		try{
			interactionInterface.sayHelloToMe(member, conent);
		}catch(Exception e){
		}
	}
	
	public void saySomethingToMe(Member member, String content)
	{
		if(interactionInterface == null)
			return;
		try{
			interactionInterface.saySomethingToMe(member, content);
		}catch(Exception e){
		}
	}

	public void receiveMessage(Member member, String message)
	{
		if(interactionInterface == null)
			return;
		try{
			interactionInterface.receiveMessage(member, message);
		}catch(Exception e){
		}
	}

	public void sendListenedSentenceToMe(Member member, String message)
	{
		if(interactionInterface == null)
			return;
		try{
			interactionInterface.sendListenedSentenceToMe(member, message);
		}catch(Exception e){
		}
	}
	
	public void discoverNewMember(Member member)
	{
		if(teamMemberInterface == null)
			return;
		try{
			int maxTimes = 5;
			while(maxTimes-- > 0 && StringUtils.isEmpty(member.getName()) && StringUtils.isEmpty(member.getCompany()))
			{
				ThreadUtils.sleep(1000);
			}
			teamMemberInterface.discoverNewMember(member);
		}catch(Exception e){
		}
	}
	
	public void log(LoggerLevel logLevel, String className, String logInfo)
	{
		if(loggerInterface == null)
			return;
		try{
			loggerInterface.log(logLevel, className, logInfo);
		}catch(Exception e){
		}
	}
	
	public void logErr(Object message, Throwable t)
	{
		if(loggerInterface == null)
			return;
		try{
			loggerInterface.logErr(message, t);
		}catch(Exception e){
		}
	}
}
