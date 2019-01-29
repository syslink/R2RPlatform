package com.pirobot.client.tools;

import com.pirobot.client.global.LoggerLevel;
import com.pirobot.client.robot.RobotWrapper;

public class LoggerUtils {
	private Class clazz = null;
	private LoggerUtils(Class clazz)
	{
		this.clazz = clazz;
	}
	public static LoggerUtils getLogger(Class clazz)
	{
		return new LoggerUtils(clazz);
	}
	public void debug(String logInfo)
	{
		RobotWrapper.getInstance().log(LoggerLevel.debug, clazz.getName(), logInfo);
	}
	public void info(String logInfo)
	{
		RobotWrapper.getInstance().log(LoggerLevel.info, clazz.getName(), logInfo);
	}
	public void warn(String logInfo)
	{
		RobotWrapper.getInstance().log(LoggerLevel.warn, clazz.getName(), logInfo);
	}
	public void error(String logInfo)
	{
		RobotWrapper.getInstance().log(LoggerLevel.error, clazz.getName(), logInfo);
	}
	public void error(Object message, Throwable t)
	{
		RobotWrapper.getInstance().logErr(message, t);
	}
}
