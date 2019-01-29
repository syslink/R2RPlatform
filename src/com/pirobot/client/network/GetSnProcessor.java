
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
import com.alibaba.fastjson.JSONObject;
import com.pirobot.client.global.Global;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.network.result.BaseResult;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

 
public class GetSnProcessor  {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(GetSnProcessor.class);

	public static void getSN()  
	{
		try{
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("deviceId",RobotWrapper.getInstance().getDeviceId());
			map.put("uid", RobotWrapper.getInstance().getUid());
			map.put("action", "0000000000000000");
			map.put("companyId", RobotWrapper.getInstance().getCompanyId());
			HttpPostProcessor.asyncHttpPost(map, Global.getInternalConfig("GET_SN_APIURL"),new Callback(){

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					logger.error(arg1.getMessage(), arg1);
				}

				@Override
				public void onResponse(Call arg0, Response arg1) throws IOException {
					if(arg1.code() == 200)
					{
						BaseResult result = JSON.parseObject(arg1.body().string(),BaseResult.class);
						JSONObject robotObj = JSONObject.parseObject(result.data.toString());
						RobotWrapper.getInstance().setSN(robotObj.getString("sn"));
						RobotWrapper.getInstance().setName(robotObj.getString("name"));
					}
					else
						logger.warn("Fail to get sn.");
				}
			});
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	 
}
