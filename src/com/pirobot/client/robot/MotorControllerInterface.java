package com.pirobot.client.robot;

public interface MotorControllerInterface {
	/**
	* 执行电机控制指令
	*@param ledInfo  电机控制信息
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-09-03 19:00:00
	*/
	public void excute(String motorInfo);
	
}
