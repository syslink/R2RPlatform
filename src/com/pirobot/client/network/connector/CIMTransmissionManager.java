package com.pirobot.client.network.connector;


import com.pirobot.cim.sdk.client.CIMEventBroadcastReceiver;
import com.pirobot.cim.sdk.client.CIMEventListener;
import com.pirobot.cim.sdk.client.CIMPushManager;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.cim.sdk.client.model.ReplyBody;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.handler.message.MessageHandlerFactory;
import com.pirobot.client.network.MessageReceiveProcessor;
import com.pirobot.client.tools.CommonUtils;
import com.pirobot.client.tools.ConfigManager;
import com.pirobot.client.tools.LoggerUtils;

public class CIMTransmissionManager implements CIMEventListener {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(CIMTransmissionManager.class);

	public static void startup() {
		/**
		 * 设置运行时参数
		 */
		CIMPushManager.setClientVersion(RobotWrapper.getInstance().getClientVersion());// 客户端程序版本
		CIMPushManager.setDeviceModel(RobotWrapper.getInstance().getDeviceModel());// 设备型号名称
		CIMPushManager.setDeviceId(RobotWrapper.getInstance().getDeviceId());// 设备ID
		CIMPushManager.setDeviceName(RobotWrapper.getInstance().getName());// 设备名称
		/*
		 * 设置消息监听回调
		 */
		CIMTransmissionManager manager = new CIMTransmissionManager();
		CIMEventBroadcastReceiver.getInstance().setGlobalCIMEventListener(manager);
		
		/**
		 * 连接RMS服务器，建立长连接
		 */
		String host = Global.getInternalConfig("CIM_HOST");
		int port = ConfigManager.getInstance().getIntValue("CIM_PORT");
		CIMPushManager.connect(host, port);
	}
	
	public static void close(){
		CIMPushManager.stop();
	}
 
	public static boolean isConnectedWithServer()
	{
		return CIMPushManager.isConnected();
	}

	@Override
	public void onConnectionClosed() {
		logger.info("onConnectionClosed");
		if(!isConnectedWithServer())
			startup();
	}

	@Override
	public void onConnectionFailed(Exception e) {
		logger.info("onConnectionFailed");
		if(!isConnectedWithServer())
			startup();
	}

	@Override
	public void onConnectionSuccessed() {
		logger.info("onConnectionSuccessed");
	}

	@Override
	public void onMessageReceived(Message message) {
		MessageReceiveProcessor.received(message.getMid());
		if(CommonUtils.isDuplicated(message.getMid()))
			return;

		logger.info(message.getContent());
		
		MessageHandlerFactory.getFactory().handle(message);

	}
	
	@Override
	public void onReplyReceived(ReplyBody replybody) {
	}
	
	
	public static   void main(String[] a) {
		 startup();
	}

}
