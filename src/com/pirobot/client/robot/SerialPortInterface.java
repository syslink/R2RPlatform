package com.pirobot.client.robot;

public interface SerialPortInterface {
	/**
	* 向串口写数据;
	*@param data： 数据内容
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void write(byte[] data);
}
