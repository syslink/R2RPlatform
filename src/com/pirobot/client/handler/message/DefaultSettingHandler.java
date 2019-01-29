package com.pirobot.client.handler.message;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pirobot.cim.sdk.client.model.Message;

public class DefaultSettingHandler implements CIMMessageHandler {

	public boolean process(Message message){
		
		
		HashMap<String ,String> map = JSON.parseObject(message.getContent(),new TypeReference<HashMap<String ,String>>(){}.getType());
        for(String key:map.keySet()){
        	
        }
		return true;
	}
 
}
