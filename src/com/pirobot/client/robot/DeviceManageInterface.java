package com.pirobot.client.robot;

public interface DeviceManageInterface {
	/**
	* 重启设备
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void reboot();
	
	/**
	* 关闭设备
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void shutdown();

	/**
	* 设置wifi账号、密码
	*@param account   wifi账号
	*@param password  wifi密码
	*@return wifi连接成功返回true，否则false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean setWifi(String account, String password);

	/**
	* 获取机器人当前电量
	*@return 1~100的数值，100为满电
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-03-14 12:31:00
	*/
	public int getElectricity();
	

	/**
	* 机器人复位，即所有部位恢复到初始状态
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-04-25 17:31:00
	*/
	public void reset();
	
	/**
	* 给机器人设置新的名字
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2018-01-05 17:11:00
	*/
	public void setRobotName(String robotName);
	
}
