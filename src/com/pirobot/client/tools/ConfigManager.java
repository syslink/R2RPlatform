/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author sam@pirobot.club
 */ 
package com.pirobot.client.tools;

import java.util.Properties;
 
public class ConfigManager {

    private  Properties config;

    private static ConfigManager instance;

    private ConfigManager() {
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                instance = new ConfigManager();
            }
        }
        return instance;
    }

    public void loadConfig() {
    	if(config==null)
    	{
	        try {
	        	config = new Properties();
	        	config.load(ConfigManager.class.getClassLoader().getResourceAsStream("resource/config.properties"));
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
    	}
    }
    
    public   String getStringValue(String key) {
    	String value =  config.getProperty(key);
        return value;
    }
    
    public   int getIntValue(String key) {
    	try{
            return  Integer.parseInt(getStringValue(key).trim());
    	}catch(Exception e)
    	{
    		return 0;
    	}
    }
}
