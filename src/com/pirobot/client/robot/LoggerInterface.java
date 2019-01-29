package com.pirobot.client.robot;

import com.pirobot.client.global.LoggerLevel;

public interface LoggerInterface {
	/**
	* 记录日志
	*@param logLevel  日志级别，四级：debug, info, warn, error
	*@param className 产出日志的类名
	*@param info 日志信息
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-09-04 12:31:00
	*/
	public void log(LoggerLevel logLevel, String className, String info);
	
	/**
	* 记录发生异常的错误日志，包含异常信息
	*@param message  基本错误信息
	*@param t 异常信息
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-09-04 12:31:00
	*/
	public void logErr(Object message, Throwable t);
}
