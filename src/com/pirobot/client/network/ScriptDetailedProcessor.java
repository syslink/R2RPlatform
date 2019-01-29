
/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author sam@pirobot.club
 */ 
package com.pirobot.client.network;
import java.io.IOException;
import java.util.HashMap;



import com.alibaba.fastjson.JSON;
import com.pirobot.client.global.Global;
import com.pirobot.client.model.Script;
import com.pirobot.client.network.result.BaseResult;
import com.pirobot.client.tools.LoggerUtils;

 
public class ScriptDetailedProcessor  {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(ScriptDetailedProcessor.class);

	public static Script get(String sid)  
	{
		HashMap<String,String> params = new HashMap<String ,String>();
		params.put("sid", sid);
		try {
			String data = HttpPostProcessor.syncHttpPost(params,Global.getInternalConfig("SCRIPT_DETAILED_APIURL"));
			data = (String) JSON.parseObject(data,BaseResult.class).data.toString();
			return JSON.parseObject(data,Script.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		
	}

	public static Script getRandom(String uid, String type) {
		HashMap<String,String> params = new HashMap<String ,String>();
		params.put("uid", uid);
		params.put("type", type);
		try {
			String data = HttpPostProcessor.syncHttpPost(params,Global.getInternalConfig("SCRIPT_RANDOM_APIURL"));
			data = (String) JSON.parseObject(data,BaseResult.class).data.toString();
			return JSON.parseObject(data,Script.class);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
    
	
}
