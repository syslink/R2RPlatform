package com.pirobot.client.robot;

public interface ScriptInterface {
	/**
	* 剧本开始执行时，会调用此接口通知应用程序，由接口实现者决定作何响应;
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void notifyScriptStart();
	/**
	* 剧本暂停执行时，会调用此接口通知应用程序，由接口实现者决定作何响应;
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void notifyScriptPause();
	/**
	* 剧本结束执行时，会调用此接口通知应用程序，由接口实现者决定作何响应;
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void notifyScriptStop();
}
