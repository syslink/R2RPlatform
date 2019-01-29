package com.pirobot.client.robot;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface EyeInterface {
	/**
	* 返回待检测物体的位置信息;
	*@param objectInfo  待检测物体信息，包括名字、特征、颜色等信息，
	*如:{"name":"face","properties":{"age":"30","gender":"female","smiling":true,"glass":true,"headpose":"yaw_angle"}} 表示要检测年龄在30岁左右，正在摇头笑的、佩戴眼镜的、女性的脸
	*{"name":"ball","properties":{"color":"yellow"}} 表示要检测颜色为黄色的球
	*{"name":"all"} 表示要检测所有能识别出来的物体
	*@return 获取物体位置信息，格式为：{{"name":"face","distance":100,"leftTopPos":"20.8,15.2","rightBottomPos":"40.2,25.1"}}
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 20:00:00
	*/
	public JSONArray detectObjectPos(JSONObject objectInfo);
}
