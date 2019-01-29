 
package com.pirobot.client.driver;

// 子类通过继承此接口，并注册后（RobotWrapper::registerDriverReceiver），便可接收到串口上报的消息
public interface DriverMessageReceiver {

	public void onDriverMessageReceived(String data);
}
