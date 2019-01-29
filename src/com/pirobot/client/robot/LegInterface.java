
package com.pirobot.client.robot;

import com.pirobot.client.global.Orientation;
import com.pirobot.client.global.PosInfo;

public interface LegInterface {
	/**
	* 往前、后、左、右运动
	*@param orientation  四种类型：forward,backward,left,right
	**@param distanceOrAngle（单位cm） 当orientation为forward,backward时表示前进、后退的距离值，当orientation为left,right时表示转身角度值
	**@param duration 表示运动持续的时间（单位ms）
	*@return 本次运动实际持续的时间（单位ms）
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public int move(Orientation orientation, int distanceOrAngle, int duration);

	/**
	* 按顺时针or逆时针方向绕圆弧运动
	*@param orientation  此处用到两种类型：clockwise,anticlockwise
	**@param radius 半径（单位cm） 
	**@param angle 表示圆弧角度（单位°）
	**@param duration 表示运动持续的时间（单位ms）
	*@return 本次运动实际持续的时间（单位ms）
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public int rotate(Orientation orientation, int radius, int angle, int duration);
	
	/**
	* 移动到指定的位置坐标或名称（当位置坐标为空时，按名称处理）
	*@param posName  位置名称
	**@param posInfo 位置坐标
	*@return 本次运动实际持续的时间（单位ms）
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-05-15 12:31:00
	*/
	public int moveToPosition(String posName, PosInfo posInfo);
}
