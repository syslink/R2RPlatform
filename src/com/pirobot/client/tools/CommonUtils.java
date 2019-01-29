package com.pirobot.client.tools;



import com.pirobot.client.robot.RobotWrapper;

public class CommonUtils {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(CommonUtils.class);
	private static String recentRcvMsgIds = new String();
	 
	public static void killMusic(){
		RobotWrapper.getInstance().getRobotActionIntegration().stopPlay();
	}
	
	public static void play(String path)
	{
		RobotWrapper.getInstance().getRobotActionIntegration().play(path);
	}
	
	public static void updateTime()
	{
		String command = "htpdate -t -s cmp.cellbot.cn";
		logger.info(command);
		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	synchronized public static boolean isDuplicated(String mid)
	{
		if(recentRcvMsgIds.contains(mid))
		{
			return true;
		}
		if(recentRcvMsgIds.length() > 512)
		{
			recentRcvMsgIds = recentRcvMsgIds.substring(256) + mid;
		}
		else
			recentRcvMsgIds += mid;
		return false;
	}
}
