package com.pirobot.client.robot;

public interface EmotionInterface {
	/**
	* 显示表情;
	*@param emotionName  表情名称，目前包括：none(常规),speaking(说话),eye(眨眼),smile,happy,angry,sad,cry,shy,heart(心动、喜欢),heart_broke(心碎)
	**@param duration 表情持续时长,单位ms
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void display(String emotionName, int duration);
	
}
