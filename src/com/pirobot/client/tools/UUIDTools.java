package com.pirobot.client.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Formatter;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;


public class UUIDTools {
	private static String mac = "";
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static String getDeviceId(){
		if(!TextUtils.isEmpty(mac))
			return mac;
		Properties props = System.getProperties();
		String osName = props.getProperty("os.name");
		if(osName.contains("Windows"))
		{
			mac = getWinMac();
		} 
		else if(osName.contains("Linux"))
		{
			if(props.toString().contains("Android"))
			{
				mac = getAndroidMac("cat /sys/class/net/wlan0/address ");
				if(StringUtils.isEmpty(mac))
				{
					mac = getAndroidMac("cat /sys/class/net/eth0/address ");
				}
			}
			else
			{
				mac = getLinuxMac();
			}
		}
					
        return mac;
	}
	public static void setDeviceId(String id)
	{
		mac = id;
	}
	private static String getAndroidMac(String networkCardPath) 
	{
	    String androidMac = null;
	    String str = "";

	    try 
	    {
	        Process process = Runtime.getRuntime().exec(networkCardPath);
	        InputStreamReader is = new InputStreamReader(process.getInputStream());
	        LineNumberReader input = new LineNumberReader(is);

	        for (; null != str; ) 
	        {
	            str = input.readLine();
	            if (str != null)
	            {
	            	androidMac = str.trim();// 去空格
	                break;
	            }
	        }
	    } catch (IOException ex) {
	        // 赋予默认值
	        ex.printStackTrace();
	    }
	    return androidMac;
	}
	private static String getLinuxMac()
	{
		String linuxMac = "";
		BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                	linuxMac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
        	IOUtils.closeQuietly(bufferedReader);
        }
        return linuxMac;
	}
	//得到计算机的ip地址和mac地址
    public static String getWinMac() {
    	String sMAC = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            //ni.getInetAddresses().nextElement().getAddress();
            byte[] mac = ni.getHardwareAddress();
            String sIP = address.getHostAddress();
            
            Formatter formatter = new Formatter();
            for (int i = 0; i < mac.length; i++) {
                sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i],
                        (i < mac.length - 1) ? "-" : "").toString();
            }
            return sMAC;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sMAC;
    }
    public static void main(String[] args)
    {
    	Properties props = System.getProperties();
    	System.out.println("Java的运行环境版本：" + props.getProperty("java.version"));
        System.out.println("Java的运行环境供应商：" + props.getProperty("java.vendor"));
        System.out.println("Java供应商的URL：" + props.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径：" + props.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本：" + props.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商：" + props.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称：" + props.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本：" + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：" + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：" + props.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本：" + props.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商：" + props.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称：" + props.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号：" + props.getProperty("java.class.version"));
        System.out.println("Java的类路径：" + props.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表：" + props.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径：" + props.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径：" + props.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
        System.out.println("操作系统的构架：" + props.getProperty("os.arch"));
        System.out.println("操作系统的版本：" + props.getProperty("os.version"));
        System.out.println("文件分隔符：" + props.getProperty("file.separator"));
        //在 unix 系统中是＂／＂
        System.out.println("路径分隔符：" + props.getProperty("path.separator"));
        //在 unix 系统中是＂:＂
        System.out.println("行分隔符：" + props.getProperty("line.separator"));
        //在 unix 系统中是＂/n＂
        System.out.println("用户的账户名称：" + props.getProperty("user.name"));
        System.out.println("用户的主目录：" + props.getProperty("user.home"));
        System.out.println("用户的当前工作目录：" + props.getProperty("user.dir"));

    }
}
