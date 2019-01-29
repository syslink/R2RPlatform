/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author sam@pirobot.club
 */ 
package com.pirobot.client.network;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;


import com.pirobot.client.global.Global;
import com.pirobot.client.tools.LoggerUtils;

 
public class MessageReceiveProcessor  {
	static HashMap<String,String> params = new HashMap<String ,String>();
	protected final static LoggerUtils logger = LoggerUtils.getLogger(MessageReceiveProcessor.class);

	public static void received(final String mid)  
	{
		if(StringUtils.isNotEmpty(mid)){
			params.put("mid", mid);
			HttpPostProcessor.asyncHttpPost(params, Global.getInternalConfig("MSG_RECEIVED_APIURL"));
		}
	}
	
}