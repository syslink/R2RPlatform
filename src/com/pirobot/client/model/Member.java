package com.pirobot.client.model;

import java.io.IOException;
import java.util.HashMap;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.client.global.Global;
import com.pirobot.client.network.HttpPostProcessor;
import com.pirobot.client.network.result.BaseResult;
import com.pirobot.client.tools.LoggerUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Member {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(Member.class);
	private String id;
	private boolean leader = false;
	volatile private String name;
	private String company;
	private long heartBeatTime = 0L;
	
	public Member(String id, boolean isLeader)
	{
		this.id = id;
		this.leader = isLeader;
		heartBeatTime = System.currentTimeMillis();
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("deviceId", id);
		HttpPostProcessor.asyncHttpPost(map, Global.getInternalConfig("GET_ROBOT_APIURL"),new Callback(){

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				logger.error(arg1.getMessage(), arg1);
			}

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try{
					BaseResult result = JSON.parseObject(arg1.body().string(), BaseResult.class);
					if(result.data != null)
					{
						JSONObject robotObj = JSONObject.parseObject(result.data.toString());
						Member.this.name = robotObj.getString("name");
						Member.this.company = robotObj.getString("companyId");
					}
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
			}
		});
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public boolean compare(Member m)
	{
		return id.equals(m.id);
	}

	public long getHeartBeatTime() {
		return heartBeatTime;
	}

	public void setHeartBeatTime(long heartBeatTime) {
		this.heartBeatTime = heartBeatTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
}
