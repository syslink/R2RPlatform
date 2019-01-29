package com.pirobot.client.robot;

import com.pirobot.client.model.Member;

public interface TeamMemberInterface {
	/**
	* 发现新成员
	*@param member 新成员信息
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-04-4 12:00:00
	*/
	public void discoverNewMember(Member member);
	
}
