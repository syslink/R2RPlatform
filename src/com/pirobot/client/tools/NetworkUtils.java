package com.pirobot.client.tools;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.pirobot.client.network.connector.CIMTransmissionManager;

public class NetworkUtils {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(NetworkUtils.class);
	public static boolean isConnectedWithServer()
	{
		return CIMTransmissionManager.isConnectedWithServer();
	}
	public static void connectServer()
	{
		CIMTransmissionManager.startup();
	}
	public static double checkInternetQuality(String internetSrvAddr)
	{
		return ping(internetSrvAddr, 10, 3000);
	}
	private static double ping(String ipAddress, int pingTimes, int timeOut) {  
        BufferedReader in = null;  
        Runtime r = Runtime.getRuntime();   
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;  
        try {  
            Process p = r.exec(pingCommand);   
            if (p == null) {    
                return 0;   
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            int connectedCount = 0;   
            String line = null;   
            while ((line = in.readLine()) != null) {    
                connectedCount += getCheckResult(line);   
            }  
            return connectedCount * 1.0 / pingTimes;  
        } catch (Exception ex) {   
        	logger.error(ex.getMessage(), ex);
            return 0;  
        } finally {   
            try {    
                in.close();   
            } catch (IOException e) { 
	        	logger.error(e.getMessage(), e); 
            }  
        }
    }
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
    private static int getCheckResult(String line) {
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(line);  
        while (matcher.find()) {
            return 1;
        }
        return 0; 
    }
    
	public void startBuildInnerNetwork()
	{
		// 1：搜寻机器人热点
		
		// 2：如果自己已连某个机器人热点，比较新热点ID，如新热点ID更小，则连新热点
		
		// 3：如果自己未连任何热点&未打开热点，连接新热点
		
		// 4：如果自己未连任何热点&打开热点，比较新热点ID，小的话则关闭自身热点，同时连新热点
	}
}
