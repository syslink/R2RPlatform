package com.pirobot.client.robot;

import java.util.List;

import com.pirobot.client.model.Member;

public interface BrainInterface {
	/**
	* 处理外部（如APP）发送给机器人的文本信息，机器人应当做听到的内容进行处理（即语音转文字这一步省了，直接给了文字信息）
	*@param text  文本内容
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void processText(String text);
	

	/**
	* 处理其它机器人发送过来的问题
	*@param member 表示是哪个成员在提问题
	*@param question  文本内容
	*@param unknownMembers  已经问过但不知答案的成员列表
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void processMemberQuestion(Member member, String question, List<Member> unknownMembers);	

	/**
	* 学习QA
	*@param question 问题
	*@param answer  答案
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void learnQA(String question, String answer);
}
