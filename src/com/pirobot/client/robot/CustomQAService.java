package com.pirobot.client.robot;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.client.global.Global;
import com.pirobot.client.global.ParserType;
import com.pirobot.client.network.HttpPostProcessor;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.PinyinProcesser;

public class CustomQAService {

	protected final static LoggerUtils logger = LoggerUtils.getLogger(CustomQAService.class);
	private static CustomQAService sInstance = null;
	private String token = "";
	private String userName = "";
	private CustomQAService(){
	}
	public static CustomQAService getInstance()
	{
		if(sInstance == null)
			sInstance = new CustomQAService();
		return sInstance;
	}
	
	public boolean login(String account, String password)
	{
		try{
			String result = HttpPostProcessor.syncHttpGet(Global.getInternalConfig("ICS_LOGIN_APIURL") + "?name=" + account + "&password=" + password);
			JSONObject resultObj = JSON.parseObject(result);
			if(resultObj.getInteger("retCode") == 0)
			{
				userName = account;
				token = resultObj.getString("token");
				return true;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	public boolean addQA(String question, String answer)
	{
		if(token.isEmpty())
			return false;
		try{
			String result = HttpPostProcessor.syncHttpGet(Global.getInternalConfig("ICS_ADD_QA_APIURL") + "?token=" + token + "&name=" + userName + "&q=" + question + "&a=" + answer);
			JSONObject resultObj = JSON.parseObject(result);
			if(resultObj.getInteger("retCode") == 0)
			{
				return true;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	public JSONObject compareSentence(String s1, String s2)
	{
		if(token.isEmpty())
			return null;
		try{
			Map<String,String> params = new HashMap<String, String>();
			params.put("token", token);
			params.put("name", userName);
			params.put("s1", s1);
			params.put("s2", s2);
			String result = HttpPostProcessor.syncHttpPost(params, Global.getInternalConfig("ICS_SENTENCE_COMPARE_APIURL"));
			JSONObject resultObj = JSON.parseObject(result);
			return resultObj;
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	public JSONArray getAnswer(String question)
	{
		try{
			if(token.isEmpty() || question.isEmpty())
				return null;
			String result = HttpPostProcessor.syncHttpGet(Global.getInternalConfig("ICS_SEARCH_APIURL") + "?token=" + token + "&name=" + userName + "&q=" + question);
			JSONObject resultObj = JSON.parseObject(result);
			if(resultObj.getInteger("retCode") == 0)
			{
				return JSONArray.parseArray(resultObj.getString("result"));
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public JSONObject parseContent(ParserType parserType , String content)
	{
		try{
			if(token.isEmpty() || parserType == null || content.isEmpty())
				return null;
			String result = HttpPostProcessor.syncHttpGet(Global.getInternalConfig("ICS_FIND_PERSON_APIURL") 
														  + "?token=" + token 
														  + "&name=" + userName 
														  + "&content=" + content
														  + "&type=" + parserType.toString());
			JSONObject resultObj = JSON.parseObject(result);
			return resultObj;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	public static void main(String[] args)
	{
		CustomQAService.getInstance().login("test", "test");
		CustomQAService.getInstance().getAnswer("一加一等于几");
	}
}
