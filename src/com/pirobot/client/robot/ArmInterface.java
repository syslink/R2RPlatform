package com.pirobot.client.robot;

public interface ArmInterface {

	/**
	* 转动单只手臂
	*@param isRightHand  是否是左臂
	**@param handInfo 手臂转动的信息，当手臂只有一个自由度时，此值可以是一个数值，如果有多个自由度，可以如此：30&50&90（可以在剧本设计页面自定义内容）
	*@return 本次运动实际持续的时间（单位ms）
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public int rotate(boolean isRightHand, String handInfo);

	/**
	* 转动两只手臂
	**@param leftHandInfo 左手臂转动的信息，当手臂只有一个自由度时，此值可以是一个数值，如果有多个自由度，可以如此：30&50&90（可以在剧本设计页面自定义内容）
	**@param rightHandInfo 右手臂转动的信息，当手臂只有一个自由度时，此值可以是一个数值，如果有多个自由度，可以如此：30&50&90（可以在剧本设计页面自定义内容）
	*@return 本次运动实际持续的时间（单位ms）
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public int rotate(String leftHandInfo, String rightHandInfo);
}
