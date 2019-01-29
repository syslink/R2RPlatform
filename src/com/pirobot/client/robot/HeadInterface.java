package com.pirobot.client.robot;

import com.pirobot.client.global.Orientation;

public interface HeadInterface {
	/**
	* 转动头部
	*@param orientation  此处用到四种类型：up,down,left,right
	**@param angle 表示转动角度（单位°）
	**@param duration 表示转动持续的时间（单位ms）
	*@return 本次运动实际持续的时间（单位ms）
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public int turn(Orientation orientation, int angle, int duration);
}
