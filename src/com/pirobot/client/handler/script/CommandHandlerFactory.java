package com.pirobot.client.handler.script;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;


import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.ThreadUtils;

public  class CommandHandlerFactory {

	static CommandHandlerFactory factory;
	HashMap<String,ScriptCommandHandler> handlers = new HashMap<String,ScriptCommandHandler>();
	protected final static LoggerUtils logger = LoggerUtils.getLogger(CommandHandlerFactory.class);
	ExecutorService  executor   = Executors.newCachedThreadPool();

    Properties properties = new Properties();   
	
	  private CommandHandlerFactory()
	  {
		    //加载各个类型消息发送析器
	        try {
				InputStream in = CommandHandlerFactory.class.getResourceAsStream("handler.properties");
	        	properties.load(in);
				IOUtils.closeQuietly(in);
	        } catch (IOException e) {   
	            logger.error(e.getMessage(), e); 
	        }   
	  }
	
	
	
	public static CommandHandlerFactory getFactory()
	{
		if(factory==null)
		{
			factory = new CommandHandlerFactory();
		}
		
		return factory;
	}
	
	
	public   boolean handle(final ScriptCommand command){

		final ScriptCommandHandler handler = getCommandHandler(command.getAction());
		if(handler!=null)
		{
			long prepareTime = command.getPrepareTime();

			ThreadUtils.sleep(prepareTime);
			
			long delayTime = command.getDelayTime();
			
			if(delayTime < 0){
				executor.execute(new Runnable() {public void run() {handler.handle(command);}});
				//ThreadUtils.sleep(Constants.minGapTimeWithTwoAction);
			}
			
			if(delayTime == 0){
				handler.handle(command);
			}
			
			if(delayTime > 0){
				executor.execute(new Runnable() {public void run() {handler.handle(command);}});
				ThreadUtils.sleep(delayTime);
			}
		} 
        return true;
	}
 
	public   ScriptCommandHandler getCommandHandler(String code)
	{
		if(handlers.get(code)==null)
		{
			try {
				handlers.put(code, (ScriptCommandHandler) Class.forName(properties.getProperty(code)).newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return handlers.get(code);
	}
	
	public void shutdownAllTask()
	{
		executor.shutdownNow();
		executor = Executors.newCachedThreadPool();
	}
}
