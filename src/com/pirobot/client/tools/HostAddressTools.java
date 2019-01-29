package com.pirobot.client.tools;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class HostAddressTools {

	public static String getHostAddress() {
		Enumeration<NetworkInterface> allNetInterfaces;  //定义网络接口枚举类  
        try {  
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();  //获得网络接口  
  
            InetAddress ip = null; //声明一个InetAddress类型ip地址  
            while (allNetInterfaces.hasMoreElements()) //遍历所有的网络接口  
            {  
                NetworkInterface netInterface = allNetInterfaces.nextElement();  
                System.out.println(netInterface.getName());  //打印网端名字  
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses(); //同样再定义网络地址枚举类  
                while (addresses.hasMoreElements())  
                {  
                    ip = addresses.nextElement();  
                    if (ip != null && (ip instanceof Inet4Address) && !ip.getHostAddress().equals("127.0.0.1")) //InetAddress类包括Inet4Address和Inet6Address  
                    {  
                       return ip.getHostAddress();
                    }   
                }  
            }  
        } catch (SocketException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        return null;
	}
	
}
