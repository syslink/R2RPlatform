package com.pirobot.client.robot;

public interface EarInterface {

	/**
	* 让机器人开始语音听写，在剧本执行过程中可能会需要调用此接口;
	*@return 能正常听写则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean startListening();

	/**
	* 让机器人停止语音听写，在剧本执行过程中可能会需要调用此接口;
	*@return 能正常听写则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean stopListening();
	
	/**
	* 返回语音听写时从起始到结束的完整的内容;
	*@param isListeningChinese  true:听中文，false:听英文
	*@return 获取听到的语句
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public String getListenedSentence(boolean isListeningChinese);


	/**
	* 是否正在进行听写;
	*@return 正在听写则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean isListening();

}
