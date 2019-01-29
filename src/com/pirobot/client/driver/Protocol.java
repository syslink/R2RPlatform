package com.pirobot.client.driver;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.client.robot.RobotWrapper;


public class Protocol {
	/** 
	          下行数据协议
		1001	直行
		1002	转动身体方向
		1003	前进转向
		1004	显示表情
		1005	获取位置
		1006	清除位置
		1007	查询障碍
		1008	电量查询
		1009	设置ID
		1010	查询周围队长ID
		1011	加入一个队
		1012	查询所属队
		1013	申请成为队长
		1014	查询本队成员
		1015	查询本队成员位置
		1016	手臂动作
		1017	头部动作
		1020          激光发射
	**/
	public static final String ACTION_1001 = "1001";
	public static final String ACTION_1002 = "1002";
	public static final String ACTION_1003 = "1003";
	public static final String ACTION_1004 = "1004";
	public static final String ACTION_1005 = "1005";
	public static final String ACTION_1006 = "1006";
	public static final String ACTION_1007 = "1007";
	public static final String ACTION_1008 = "1008";
	public static final String ACTION_1009 = "1009";
	public static final String ACTION_1010 = "1010";
	public static final String ACTION_1011 = "1011";
	public static final String ACTION_1012 = "1012";
	public static final String ACTION_1013 = "1013";
	public static final String ACTION_1014 = "1014";
	public static final String ACTION_1015 = "1015"; 
	public static final String ACTION_1016 = "1016"; 
	public static final String ACTION_1017 = "1017"; 
	public static final String ACTION_1020 = "1020"; 

	/**
	          上行数据协议
	    1111	上报错误下发的数据
	    1112	上报已正确下发的数据
	    2001	上报坐标
		2002	障碍上报
		2003	防跌上报
		2004	电量上报
		2005	周围队长号上报
		2006	自己队长号上报
		2007	本队成员ID上报
		2008	本队成员位置上报
	 */

	public static final String ACTION_1111 = "1111";
	public static final String ACTION_1112 = "1112";
	public static final String ACTION_2001 = "2001";
	public static final String ACTION_2002 = "2002";
	public static final String ACTION_2003 = "2003";
	public static final String ACTION_2004 = "2004";
	public static final String ACTION_2005 = "2005";
	public static final String ACTION_2006 = "2006";
	public static final String ACTION_2007 = "2007";
	public static final String ACTION_2008 = "2008";
	public static final String ACTION_2012 = "2012";
	public static final String ACTION_2014 = "2014";  // 确认数据包
	
	
	public static String buildMoveData(String orientation, int distance, int duration)
	{
	 	JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1001);
		cmdObj.put("orientation", orientation);
		cmdObj.put("distance", distance);
		cmdObj.put("duration", duration);
		return cmdObj.toJSONString(); 
	}
	
	public static String buildTurnData(String orientation, int angle, int duration)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("orientation", orientation);
		cmdObj.put("action", ACTION_1002);
		cmdObj.put("duration", duration + "");
		cmdObj.put("angle", angle);
		cmdObj.put("raduis", null);
		
		return cmdObj.toJSONString();
	}
	
	public static String buildHeadTurnData(String orientation, int angle, int duration)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("orientation", orientation);
		cmdObj.put("action", ACTION_1017);
		cmdObj.put("duration", duration + "");
		cmdObj.put("angle", angle);
		
		return cmdObj.toJSONString();
	}
	
	public static String buildRotateData(String orientation, int angle, long duration,int radius)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("orientation", orientation);
		cmdObj.put("action", ACTION_1003);
		cmdObj.put("duration", duration);
		cmdObj.put("angle", angle);
		cmdObj.put("radius", radius);
		
		return cmdObj.toJSONString();
	}
	
	public static String buildEmotion(String name,  long duration)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("name", name);
		cmdObj.put("action", ACTION_1004);
		cmdObj.put("duration", duration);
		return cmdObj.toJSONString();
	}
	
	public static String tobeLeader(boolean isLeader)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("leader", isLeader ? 1 : 0);
		cmdObj.put("action", ACTION_1013);
		 
		return cmdObj.toJSONString();
	}
	
	public static String getLocation()
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1005);
		cmdObj.put("cycle", 60000);
		return cmdObj.toJSONString();
	}
	
	public static String bindDeviceId()
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1009);
		cmdObj.put("deviceId", RobotWrapper.getInstance().getDeviceId());
		return cmdObj.toJSONString();
	}
	
	public static String getBattery()
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1008);
		cmdObj.put("cycle", 60000);
		return cmdObj.toJSONString();
	}
	
	public static String joinTeam(String id)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1011);
		cmdObj.put("leader", id);
		return cmdObj.toJSONString();
	}
	
	public static String getTeamMembers()
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1015);
		cmdObj.put("leader", RobotWrapper.getInstance().getSN());
		return cmdObj.toJSONString();
	}
	
	public static String buildHandData(String leftAngel, String rightAngel) {
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1016);
		cmdObj.put("leftangle", leftAngel);
		cmdObj.put("leftduration", 2000);
		cmdObj.put("rightangle", rightAngel);
		cmdObj.put("rightduration", 2000);
		return cmdObj.toJSONString();
	}
	
	public static String bulidFireInfo(int time/*ms*/)
	{
		JSONObject cmdObj = new JSONObject();
		cmdObj.put("action", ACTION_1020);
		cmdObj.put("fire", time);
		return cmdObj.toJSONString();
	}
 
}
