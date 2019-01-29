package com.pirobot.client.robot;

public interface CombineActionsInterface {
	/**
	* 执行组合动作
	*@param combineActionsName  组合动作名称
	*@param extraContent  组合动作附带的额外信息
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void excute(String combineActionsName, String extraContent);
	
	/**
	* 执行带音乐的组合动作，音乐是否播放可以设置
	*@param combineActionsName  组合动作名称
	*@param bMusic  音乐是否播放
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void excute(String combineActionsName, String extraContent, boolean bMusic);
	
}
