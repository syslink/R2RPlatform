/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author sam@pirobot.club
 */ 
package com.pirobot.client.network;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



import com.alibaba.fastjson.JSON;
import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.tools.LoggerUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

 
public class HttpPostProcessor  {
	private static final okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
	protected final static LoggerUtils logger = LoggerUtils.getLogger(HttpPostProcessor.class);
	 
	
	private static Request   build(Map<String,String> params,String url) {

	 
	
		FormBody.Builder build = new FormBody.Builder(); 
		params.put("API_AUTH_KEY", Global.getInternalConfig("API_AUTH_KEY"));
		if(params!=null){
			for(String key:params.keySet()){
				String value =params.get(key);
				if(value!=null){
					build.add(key, value);
				}
	        }
		}
		Request request = new Request.Builder().addHeader("API_AUTH_KEY", Global.getInternalConfig("API_AUTH_KEY")).url(url).post(build.build()).build();
        return request;
	}

    public static String syncHttpGet(String url)
    {
    	try{
    		OkHttpClient httpclient = builder.connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS).build();
        	Request request = new Request.Builder().url(url).build();
        	Response response = httpclient.newCall(request).execute();
        	if (response.isSuccessful())
        		return response.body().string();
    	}catch(Exception e){
    		logger.error(e.getMessage(), e);
    	}
    	return "";
    }
    
    public static void asyncHttpGet(String url,Callback callback){
    	
    	OkHttpClient httpclient = builder.build();
    	Request request = new Request.Builder().url(url).get().build();
    	httpclient.newCall(request).enqueue(callback);
	}
   
    public static void asyncHttpPost(HashMap<String,String> params,String url,Callback callback){
    	
    	OkHttpClient httpclient = builder.build();
    	Request request = build(params, url);
    	httpclient.newCall(request).enqueue(callback);
	}

 
    public static void asyncHttpPost(String url,String content,Callback callback){
   	
	   	OkHttpClient httpclient = builder.build();
	   	RequestBody requestBody =RequestBody.create(MediaType.parse("application/json"), content);
		Request request = new Request.Builder().addHeader("API_AUTH_KEY", Global.getInternalConfig("API_AUTH_KEY")).url(url).post(requestBody).build();
	   	httpclient.newCall(request).enqueue(callback);
	}


    public static void asyncHttpPost(Map<String,String> params,String url){
    	
    	OkHttpClient httpclient = builder.build();
    	Request request = build(params, url);
    	httpclient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response response) throws IOException {
				String data = response.body().string();
			}
			
			@Override
			public void onFailure(Call arg0, IOException e) {
			}
		});
	}
    public static String syncHttpPost(Map<String,String> params,String url) throws IOException{
		OkHttpClient httpclient = builder.connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS).build();
		Request request = build(params, url);
		Response response = httpclient.newCall(request).execute();
        String data = response.body().string();
        
        logger.info("API_RESULT:"+data);
        
        return data;
	}
    public static <T> T syncHttpPost(Map<String,String> params,String url,Class<T> resultClass) throws IOException{
		OkHttpClient httpclient = builder.connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.MILLISECONDS).build();
		Request request = build(params, url);
		Response response = httpclient.newCall(request).execute();
        String data = response.body().string();
        
        return JSON.parseObject(data, resultClass);
	}
  
}
