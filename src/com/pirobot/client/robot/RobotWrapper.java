package com.pirobot.client.robot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.pirobot.cim.sdk.client.CIMPushManager;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.driver.DriverMessageReceiver;
import com.pirobot.client.driver.Protocol;
import com.pirobot.client.global.*;
import com.pirobot.client.model.Member;
import com.pirobot.client.network.GetSnProcessor;
import com.pirobot.client.network.HttpPostProcessor;
import com.pirobot.client.network.MessageSendProcessor;
import com.pirobot.client.network.connector.CIMTransmissionManager;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.network.result.BaseResult;
import com.pirobot.client.team.NewMemberJoinedNotice;
import com.pirobot.client.team.TeamMemberManager;
import com.pirobot.client.tools.ConfigManager;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.OssUtils;
import com.pirobot.client.tools.PinyinTool;
import com.pirobot.client.tools.ThreadUtils;
import com.pirobot.client.tools.UUIDTools;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
public class RobotWrapper implements DriverMessageReceiver{
	protected final static LoggerUtils logger = LoggerUtils.getLogger(RobotWrapper.class);
	private  ExecutorService  executor = Executors.newCachedThreadPool();
	private  static RobotWrapper sInstance;
	private  Set<DriverMessageReceiver> receivers = new HashSet<DriverMessageReceiver> ();
	private List<String> cmdList = new ArrayList<String>();
	private volatile boolean isRecordCmd = false;
	private String defaultChSpeakerName = "";
	private String defaultEnSpeakerName = "";
	private volatile String name = "";
	private String sn = "";
	private String companyId = "";
	private String uid = "";
	private RobotActionIntegration robotActionIntegration = new RobotActionIntegration();
	
	private RobotWrapper(){
	}
	public static RobotWrapper getInstance()
	{
		if(sInstance == null)
			sInstance = new RobotWrapper();
		return sInstance;
	}
	public RobotActionIntegration getRobotActionIntegration()
	{
		return this.robotActionIntegration;
	}
	// 平台SDK初始化
	// 1：初始化公司id，用户id，缺省的中英文发音人
	// 2：登录CMP平台，跟剧本设计平台对接
	// 3：初始化UDP服务
	// 4：设置机器人SDK初始化模式
	// 5：获取此台机器人的sn编号
	// 6：连接RMS平台，使机器人拥有即时通信功能，即使不在同一局域网，也能进行通信和信息共享
	// 7：向同一局域网的机器人通知本机器人已加入
	public boolean init(String companyId, String uid, String account, String password, String defaultChSpeakerName, String defaultEnSpeakerName)
	{
		this.companyId = companyId;
		this.uid = uid;
		this.defaultChSpeakerName = defaultChSpeakerName;
		this.defaultEnSpeakerName = defaultEnSpeakerName;
		if(!login(account, password))
			return false;
		
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				UDPTransmissionManager.getInstance().bind();
				
				Global.switchSpeakingMode(false);
				Global.switchNormalMode();

				GetSnProcessor.getSN();
				CIMTransmissionManager.startup();
				syncRobotName();
				NewMemberJoinedNotice.notifyOthers();
				PinyinTool.init();
			}
		});
		
		return true;
	}
	
	private boolean login(String account, String password) {
		HashMap<String,String> params = new HashMap<String ,String>();
		params.put("account", account);
		params.put("password", password);
		try {
			String data = HttpPostProcessor.syncHttpPost(params,Global.getInternalConfig("USER_LOGIN_APIURL"));
			BaseResult result = (BaseResult)JSON.parseObject(data, BaseResult.class);
			return result != null && result.code == 200;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	private void syncRobotName()
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("deviceId", getDeviceId());
		HttpPostProcessor.asyncHttpPost(map, Global.getInternalConfig("GET_ROBOT_APIURL"),new Callback(){

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				logger.error(arg1.getMessage(), arg1);
			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				BaseResult result = JSON.parseObject(arg1.body().string(), BaseResult.class);
				JSONObject robotObj = JSONObject.parseObject(result.data.toString());
				RobotWrapper.this.name = robotObj.getString("name");
				RobotWrapper.this.companyId = robotObj.getString("companyId");
			}
		});
	}

	public  void registerDriverReceiver(DriverMessageReceiver receiver){
		receivers.add(receiver);
	}
	
	public  void unregisterDriverReceiver(DriverMessageReceiver receiver){
		receivers.remove(receivers);
	}
	public String getCompanyId()
	{
		return companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		TeamMemberManager.getInstance().getMember(this.getDeviceId()).setName(name);
		robotActionIntegration.setRobotName(name);
	}		
	public String getSN() {
		return sn;
	}
	public void setSN(String sn) {
		this.sn = sn;
	}
	
	public String getUid() {
		return uid;
	}
	public String getDefaultChSpeakerName() {
		return defaultChSpeakerName;
	}
	public void setDefaultChSpeakerName(String defaultChSpeakerName) {
		this.defaultChSpeakerName = defaultChSpeakerName;
	}
	public String getDefaultEnSpeakerName() {
		return defaultEnSpeakerName;
	}
	public void setDefaultEnSpeakerName(String defaultEnSpeakerName) {
		this.defaultEnSpeakerName = defaultEnSpeakerName;
	}
	
	public  String getDeviceId(){
		return UUIDTools.getDeviceId();
	}
	
	public void setDeviceId(String id){
		UUIDTools.setDeviceId(id);
	}
	
	public  String getDeviceModel(){
		return ConfigManager.getInstance().getStringValue("DEVICE_MODEL");
	}
	
	public  String getClientVersion(){
		return ConfigManager.getInstance().getStringValue("CLIENT_VERSION");
	}
	
	public void setArmInterface(ArmInterface armInterface) {
		robotActionIntegration.setArmInterface(armInterface);
	}


	public void setBrainInterface(BrainInterface brainInterface) {
		robotActionIntegration.setBrainInterface(brainInterface);
	}


	public void setDeviceManageInterface(DeviceManageInterface deviceManageInterface) {
		robotActionIntegration.setDeviceManageInterface(deviceManageInterface);
	}


	public void setEarInterface(EarInterface earInterface) {
		robotActionIntegration.setEarInterface(earInterface);
	}


	public void setEmotionInterface(EmotionInterface emotionInterface) {
		robotActionIntegration.setEmotionInterface(emotionInterface);
	}

	public void setCombineActionsInterface(CombineActionsInterface combineActionsInterface)
	{
		robotActionIntegration.setCombineActionsInterface(combineActionsInterface);
	}
	
	public void setEyeInterface(EyeInterface eyeInterface) {
		robotActionIntegration.setEyeInterface(eyeInterface);
	}


	public void setHeadInterface(HeadInterface headInterface) {
		robotActionIntegration.setHeadInterface(headInterface);
	}


	public void setLegInterface(LegInterface legInterface) {
		robotActionIntegration.setLegInterface(legInterface);
	}


	public void setMouthInterface(MouthInterface mouthInterface) {
		robotActionIntegration.setMouthInterface(mouthInterface);
	}


	public void setScriptInterface(ScriptInterface scriptInterface) {
		robotActionIntegration.setScriptInterface(scriptInterface);
	}

	public void setSerialPortInterface(SerialPortInterface serialPortInterface) {
		robotActionIntegration.setSerialPortInterface(serialPortInterface);
	}
	
	public void setInteractionInterface(InteractionInterface interactionInterface) {
		robotActionIntegration.setInteractionInterface(interactionInterface);
	}
	public void setTeamMemberInterface(TeamMemberInterface teamMemberInterface) {
		robotActionIntegration.setTeamMemberInterface(teamMemberInterface);
	}
	public void setLedControllerInterface(LedControllerInterface ledControllerInterface)
	{
		robotActionIntegration.setLedControllerInterface(ledControllerInterface);
	}
	public void setMotorControllerInterface(MotorControllerInterface MotorControllerInterface)
	{
		robotActionIntegration.setMotorControllerInterface(MotorControllerInterface);
	}
	public boolean excuteActionCmd(String actionInfo)
	{
		JSONObject actionObj = JSON.parseObject(actionInfo);
		String action = actionObj.getString("action");
		if(action.equals(Protocol.ACTION_1001))
		{
			String orientation = actionObj.getString("orientation");
			if(orientation.equals("stop"))
				move(orientation, 0, 0);
			else
				move(orientation, actionObj.getIntValue("distance"), actionObj.getIntValue("duration"));
		}
		else if(action.equals(Protocol.ACTION_1002))
		{
			turn(actionObj.getString("orientation"), actionObj.getIntValue("angle"), actionObj.getIntValue("duration"));
		}
		else if(action.equals(Protocol.ACTION_1003))
		{
			rotate(actionObj.getString("orientation"), actionObj.getIntValue("angle"), actionObj.getIntValue("duration"), actionObj.getIntValue("radius"));
		}
		else if(action.equals(Protocol.ACTION_1016))
		{
			hand(actionObj.getString("leftangle"), actionObj.getString("rightangle"));
		}
		else if(action.equals(Protocol.ACTION_1017))
		{
			turnHead(actionObj.getString("orientation"), actionObj.getIntValue("angle"), actionObj.getIntValue("duration"));
		}
		else
			return false;
		return true;
	}
	
	public int move(String orientation, int distance, int time ){		
		String command = Protocol.buildMoveData(orientation, distance, time);
		this.addCmd(command);
    	broadcastActionInTeam(command);
    	return robotActionIntegration.move(Orientation.fromString(orientation), distance, time);    	
	}
	
	public int moveToPosition(String posName, PosInfo posInfo){		
    	return robotActionIntegration.moveToPosition(posName, posInfo);    	
	}
	
	public int turnHand(String handInfo, boolean bRightHand)
	{
		int totalTime = 0;
		String[] handInfoElements = handInfo.split(",|，");
		int length = handInfoElements.length;
		for(int i = 0; i < length; i++)
		{
			if(i % 2 == 0)
			{
				if(bRightHand)
					totalTime += hand("", handInfoElements[i]);
				else
					totalTime += hand(handInfoElements[i], "");
			}
			else
			{
				int spanTime = Integer.parseInt(handInfoElements[i]);
				totalTime += spanTime;
				ThreadUtils.sleep(spanTime);
			}
		}
		return totalTime;
	}
	public int hand(String leftAngle, String rightAngle){
		String command = Protocol.buildHandData(leftAngle, rightAngle);
		this.addCmd(command);
    	broadcastActionInTeam(command);
    	if(leftAngle.isEmpty() || rightAngle.isEmpty())
    		return robotActionIntegration.rotate(!rightAngle.isEmpty(), rightAngle.isEmpty() ? leftAngle : rightAngle);
    	else
    		return robotActionIntegration.rotate(leftAngle, rightAngle);
	}
	public int exeHeadTurnList(String headTurnListInfo)
	{
		int totalTime = 0;
		String[] headTurnInfoElements = headTurnListInfo.split(",|，");
		int length = headTurnInfoElements.length;
		for(int i = 0; i < length; i++)
		{
			if(headTurnInfoElements[i].length() < 3)
				continue;
			char head = headTurnInfoElements[i].charAt(0);
			int value = Integer.parseInt(headTurnInfoElements[i].substring(2));
			int costTime = 0;
			switch(head)
			{
			case 'u':
				costTime = turnHead("up", value, 0);
				totalTime += costTime;
				break;
			case 'd':
				costTime = turnHead("down", value, 0);
				totalTime += costTime;
				break;
			case 'l':
				costTime = turnHead("left", value, 0);
				totalTime += costTime;
				break;
			case 'r':
				costTime = turnHead("right", value, 0);
				totalTime += costTime;
				break;
			default:  // sleep
				value = Integer.parseInt(headTurnInfoElements[i]);
				totalTime += value;
				ThreadUtils.sleep(value);
			}
		}
		return totalTime;
	}
	public int exeMoveList(String moveListInfo)
	{
		int totalTime = 0;
		String[] moveInfoElements = moveListInfo.split(",|，");
		int length = moveInfoElements.length;
		for(int i = 0; i < length; i++)
		{
			if(moveInfoElements[i].length() < 3)
				continue;
			char head = moveInfoElements[i].charAt(0);
			int value = Integer.parseInt(moveInfoElements[i].substring(2));
			int costTime = 0;
			switch(head)
			{
			case 'f':
				costTime = move("forward", value, 0);
				totalTime += costTime;
				ThreadUtils.sleep(costTime);
				break;
			case 'b':
				costTime = move("backward", value, 0);
				totalTime += costTime;
				ThreadUtils.sleep(costTime);
				break;
			case 'l':
				costTime = turn("left", value, 0);
				totalTime += costTime;
				ThreadUtils.sleep(costTime);
				break;
			case 'r':
				costTime = turn("right", value, 0);
				totalTime += costTime;
				ThreadUtils.sleep(costTime);
				break;
			default:  // sleep
				value = Integer.parseInt(moveInfoElements[i]);
				totalTime += value;
				ThreadUtils.sleep(value);
			}
		}
		return totalTime;
	}
	
	public int turn(String orientation,int angle,int time ){		
 		String command = Protocol.buildTurnData(orientation, angle, time);
 		this.addCmd(command);
    	broadcastActionInTeam(command);
    	return robotActionIntegration.move(Orientation.fromString(orientation), angle, time);    	
	}
	
	public int turnHead(String orientation, int angle, int duration)
	{
		String command = Protocol.buildTurnData(orientation, angle, duration);
 		this.addCmd(command);
    	broadcastActionInTeam(command);
    	return robotActionIntegration.turnHead(Orientation.fromString(orientation), angle, duration);    	
	}
	
	public int rotate(String orientation, int angle, int duration, int radius ){
		String command = Protocol.buildRotateData(orientation, angle, duration, radius);
		this.addCmd(command);
    	broadcastActionInTeam(command);
    	return robotActionIntegration.rotate(Orientation.fromString(orientation), radius, angle, duration);
	}
	
	public void showEmotion(String name, int duration){
		if(StringUtils.isEmpty(name) || duration == 0)
			return;
		this.robotActionIntegration.displayEmotion(name, duration);
		//broadcastActionInTeam(command);
	}

	public void excuteCombineActions(String combineActionName, String extraContent){
		if(StringUtils.isEmpty(combineActionName) || combineActionName.equals("none"))
			return;
		this.robotActionIntegration.excuteCombineActions(combineActionName, extraContent);
		//broadcastActionInTeam(command);
	}

	public void excuteCombineActions(String combineActionName, String extraContent, boolean bMusic){
		if(StringUtils.isEmpty(combineActionName) || combineActionName.equals("none"))
			return;
		this.robotActionIntegration.excuteCombineActions(combineActionName, extraContent, bMusic);
		//broadcastActionInTeam(command);
	}
	
	public void showEmotion(String name){
		showEmotion(name, 1000);
		//broadcastActionInTeam(command);
	}
	
	public void hideEmotion(){
	}

	public void controlLed(String ledInfo){
		if(StringUtils.isEmpty(ledInfo) || ledInfo.equals("none") || ledInfo.equals("0"))
			return;
		this.robotActionIntegration.controlLed(ledInfo);
	}
	public void controlMotor(String motorInfo){
		if(StringUtils.isEmpty(motorInfo) || motorInfo.equals("none") || motorInfo.equals("0"))
			return;
		this.robotActionIntegration.controlMotor(motorInfo);
	}
	public void log(LoggerLevel logLevel, String className, String logInfo)
	{
		if(StringUtils.isEmpty(logInfo))
			return;
		this.robotActionIntegration.log(logLevel, className, logInfo);
	}
	public void logErr(Object message, Throwable t)
	{
		if((message == null || StringUtils.isEmpty(message.toString())) && t == null)
			return;
		this.robotActionIntegration.logErr(message, t);
	}
	public void broadcastActionInTeam(String command)
	{
		if(Global.getScriptModel() != null && RobotWrapper.getInstance().getDeviceId().equals(Global.getScriptModel().getLeaderId()))  //正在执行剧本
		{
			List<String> receiverList=  Global.getScriptModel().getOtherDeviceIds();
			Message message = new Message();
			message.setAction(Constants.MessageAction.ACTION_402);
			message.setFormat(Constants.MessageFromat.FROMAT_TXT);
			message.setMid(UUIDTools.randomUUID());
			message.setContent(command);
			message.setReceiver(StringUtils.join(receiverList.toArray(),","));
			
			message.setSender(RobotWrapper.getInstance().getDeviceId());
	
			UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
			logger.info("在Team中广播剧本中队长的动作：" + command);
		}
		else if((TeamMemberManager.getInstance().getSelfInfo() != null && TeamMemberManager.getInstance().getSelfInfo().isLeader())) // 队长
		{
			List<Member> memberList = TeamMemberManager.getInstance().getOtherMemberList();			
			if(memberList.size() > 0)
			{
				List<String> receiverList = new ArrayList<String>();
				for(Member m : memberList)
					receiverList.add(m.getId());
				Message message = new Message();
				message.setAction(Constants.MessageAction.ACTION_402);
				message.setFormat(Constants.MessageFromat.FROMAT_TXT);
				message.setMid(UUIDTools.randomUUID());
				message.setContent(command);
				message.setReceiver(StringUtils.join(receiverList.toArray(), ","));
				
				message.setSender(RobotWrapper.getInstance().getDeviceId());
		
				UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
				logger.info("定位：在Team中广播队长动作：" + command);
			}
		}
		else if(TeamMemberManager.getInstance().getSelfInfo() != null)  // 队员
		{
			Member leader = TeamMemberManager.getInstance().getLeader();
			if(leader != null)
			{
				Message message = new Message();
				message.setAction(Constants.MessageAction.ACTION_405);
				message.setFormat(Constants.MessageFromat.FROMAT_TXT);
				message.setMid(UUIDTools.randomUUID());
				message.setContent(command);
				message.setReceiver(leader.getId());
				
				message.setSender(RobotWrapper.getInstance().getDeviceId());
		
				UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));
				logger.info("定位：将自身动作发给队长：" + command);
			}
		}
	}
	
	public void broadcastLeaderInfo()
	{
		JSONObject leaderInfo = new JSONObject();
		leaderInfo.put("leaderId", RobotWrapper.getInstance().getDeviceId());
		
		Message message = new Message();
		message.setAction(Constants.MessageAction.ACTION_404);
		message.setFormat(Constants.MessageFromat.FROMAT_TXT);
		message.setMid(UUIDTools.randomUUID());
		message.setContent(leaderInfo.toJSONString());
		message.setReceiver(UDPTransmissionManager.allMembers);
		
		message.setSender(RobotWrapper.getInstance().getDeviceId());

		UDPTransmissionManager.getInstance().send(JSON.toJSONString(message));	
		logger.info("在局域网中广播队长信息：" + JSON.toJSONString(message));
	}
	@Override
	public void onDriverMessageReceived(String data) {
		if(data == null || data.isEmpty())
			 return;
		 
		 for(DriverMessageReceiver receiver:receivers){
			 receiver.onDriverMessageReceived(data);
		 }
	}
	public void setRecordCmd(boolean isRecordCmd) {
		this.isRecordCmd = isRecordCmd;
	}
	synchronized public void addCmd(String cmd)
	{
		if(this.isRecordCmd)
		{
			cmdList.add(cmd);
		}
	}
	public void clearCmdList()
	{
		cmdList.clear();
	}
	public void excuteRecordedCmd()
	{
		for(String cmd : cmdList)
		{
			excuteActionCmd(cmd);
		}
	}
}
