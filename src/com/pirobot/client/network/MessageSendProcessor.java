
/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author sam@pirobot.club
 */ 
package com.pirobot.client.network;
import java.util.HashMap;



import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.global.Global;
import com.pirobot.client.tools.LoggerUtils;

 
public class MessageSendProcessor  {
	static HashMap<String,String> params = new HashMap<String ,String>();
	protected final static LoggerUtils logger = LoggerUtils.getLogger(MessageSendProcessor.class);

	public static void send(final Message message)  
	{
		params.put("action", message.getAction());
		params.put("content", message.getContent());
		params.put("format", message.getFormat());
		params.put("receiver", message.getReceiver());
		params.put("sender", message.getSender());
		params.put("mid", message.getMid());
		HttpPostProcessor.asyncHttpPost(params,Global.getInternalConfig("MSG_SEND_APIURL"));
	}
  
}
