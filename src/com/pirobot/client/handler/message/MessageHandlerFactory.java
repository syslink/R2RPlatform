package com.pirobot.client.handler.message;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;


import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.tools.LoggerUtils;

public  class MessageHandlerFactory {

	static MessageHandlerFactory factory;
	HashMap<String,CIMMessageHandler> handlers = new HashMap<String,CIMMessageHandler>();
	protected final static LoggerUtils logger = LoggerUtils.getLogger(MessageHandlerFactory.class);
	private static ExecutorService  executor   = Executors.newCachedThreadPool();

    Properties properties = new Properties();   
	
	  private MessageHandlerFactory()
	  {
		    //加载各个类型消息发送析器
	        try {
				InputStream in = MessageHandlerFactory.class.getResourceAsStream("handler.properties");
	        	properties.load(in);
				IOUtils.closeQuietly(in);
	        } catch (IOException e) {   
	            e.printStackTrace();   
	            logger.error(e.getMessage());
	        }   
	  }
	
	
	
	public static MessageHandlerFactory getFactory()
	{
		if(factory==null)
		{
			factory = new MessageHandlerFactory();
		}
		
		return factory;
	}
	
	public   boolean handle(final Message message){
//		if(System.currentTimeMillis() - message.getTimestamp() > 10000)
//		{
//			logger.info("接收到过时的消息，消息时间为:" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(message.getTimestamp())));
//			logger.info("消息内容为:" + message);
//			return false;
//		}
		final CIMMessageHandler handler = getMessageHandler(message.getAction());
		if(handler!=null)
		{
			try{
				executor.execute(new Runnable() {
					public void run() {
						handler.process(message);
					}
				});
				
			    return true;
			}catch (Exception e) {
	    		logger.error(e.getMessage(), e);
			}
		} 
        return false;
	}
 
	public   CIMMessageHandler getMessageHandler(String code)
	{
		if(handlers.get(code)==null)
		{
			try {
				handlers.put(code, (CIMMessageHandler) Class.forName(properties.getProperty(code)).newInstance());
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		return handlers.get(code);
	}
 
}
