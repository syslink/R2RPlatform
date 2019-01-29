package com.pirobot.client.handler.message;


import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.model.Wifi;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;

public class WifiSettingHandler   extends ScriptCommandMessageHandler {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(WifiSettingHandler.class);
	public boolean process(Message message){
		
		Wifi wifi  = JSON.parseObject(message.getContent(), Wifi.class);

		logger.info("新WIFI设置: ssid:"+wifi.ssid +",password:"+wifi.password);
		
		RobotWrapper.getInstance().getRobotActionIntegration().setWifi(wifi.ssid, wifi.password);
       
		return true;
	}
 
	
}
