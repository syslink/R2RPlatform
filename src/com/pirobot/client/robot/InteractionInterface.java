package com.pirobot.client.robot;

import com.pirobot.client.model.Member;

public interface InteractionInterface {
	/**
	* 有成员在刚碰面时打招呼
	*@param member 表示是哪个成员在打招呼
	*@param content 成员打招呼时说的话
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-27 12:00:00
	*/
	public void sayHelloToMe(Member member, String content);
	

	/**
	* 有成员跟我说话
	*@param member 表示是哪个成员在跟我说话
	*@param content 成员说话的内容
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-27 12:00:00
	*/
	public void saySomethingToMe(Member member, String content);

	/**
	* 有成员把它听到的话发送给我
	*@param member 表示是哪个成员听到的话
	*@param content 成员听到的内容
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-27 12:00:00
	*/
	public void sendListenedSentenceToMe(Member member, String content);
	
	/**
	* 有成员向我发消息
	*@param member 表示是哪个成员在给我发消息
	*@param message 成员发送的消息内容
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-27 12:00:00
	*/
	public void receiveMessage(Member member, String message);	
}
