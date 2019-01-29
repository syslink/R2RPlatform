package com.pirobot.client.network.connector;
 

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.pirobot.cim.sdk.client.model.Message;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.handler.message.MessageHandlerFactory;
import com.pirobot.client.tools.CommonUtils;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.UDPBordcastAppender;

/**
 * 连接服务端管理，cim核心处理类，管理连接，以及消息处理
 * 
 * @author sam@pirobot.club
 */
public class UDPTransmissionManager  {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(UDPTransmissionManager.class);
	private DatagramSocket datagramSocket ;;
	static UDPTransmissionManager manager;
    int UPD_PORT = 45678;
    final String UDP_HOST = "255.255.255.255";
    final int BUFFER_SIZE  = 2048;
    public static String allMembers = "all";
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	private UDPTransmissionManager() {
		
	}

	public void send(String host, int port, String message)
	{
		try {
			InetAddress hostAddress = InetAddress.getByName(host);
			byte bytes[] = message.getBytes();
			DatagramPacket out = new DatagramPacket(bytes, bytes.length, hostAddress, port);
            datagramSocket.send(out);
           
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	synchronized public void send(String message){
		try {
			InetAddress hostAddress = InetAddress.getByName(UDP_HOST);
			byte bytes[] = message.getBytes();
			DatagramPacket out = new DatagramPacket(bytes, bytes.length, hostAddress,UPD_PORT);
            datagramSocket.send(out);
           
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
        
	}
	public void bind(){
		try {
			
			datagramSocket = new DatagramSocket(null);
			datagramSocket.setSendBufferSize(BUFFER_SIZE);
			datagramSocket.setReceiveBufferSize(BUFFER_SIZE);
			datagramSocket.setReuseAddress(true);
			datagramSocket.bind(new InetSocketAddress(UPD_PORT));
			
			executor.execute(new Runnable() {
	            @Override
	            public void run() {
	            	 while(datagramSocket!=null && !datagramSocket.isClosed()) {
	                     byte[] buf = new byte[2048];
	                     DatagramPacket packet = new DatagramPacket(buf, buf.length);
	                     try {
	                         datagramSocket.receive(packet);
	                         onMessageReceived(packet);
	                     } catch (Exception e) {
	                         break;
	                     }
	                 }
	            }
	        });
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
	 
	}
	
	
	public void rebind(){
		unbind();
		bind();
	}
	
	public void unbind(){
		datagramSocket.close();
	}
	
	private void onMessageReceived(DatagramPacket packet){
		 final String data  = new String(packet.getData(),0,packet.getLength());
		 if(data.startsWith(UDPBordcastAppender.LOGMARK)){
			 return ;
		 }
		 executor.execute(new Runnable() {
			@Override
			public void run() {
		         onMessageReceived(data);
			}
		});
	}
	
	public void onMessageReceived(String data) {
		
		try{
			Message message = JSON.parseObject(data, Message.class);
			if(CommonUtils.isDuplicated(message.getMid()))
				return;
			
			if(!message.getAction().equals("401")) // 由于队长发送的位置信息非常频繁，因此不打印此类信息
				logger.debug("收到广播消息：" + data);
			if(message.getAction().equals("408")) 
				logger.debug("收到心跳包：" + message.getSender());
			// 忽略自己发送的消息
			if (RobotWrapper.getInstance().getDeviceId().equals(message.getSender())) {
				if(!message.getAction().equals("401"))
					logger.debug("忽略自己发送的消息");
				return ;
			}

			// 忽略不该由自己处理的消息
			if (message.getReceiver() != null 
			&& !message.getReceiver().equals(allMembers)
			&& !message.getReceiver().contains(RobotWrapper.getInstance().getDeviceId())) {
				if(!message.getAction().equals("401"))
					logger.debug("忽略不该自己处理的消息");
				return ;
			}
			
			MessageHandlerFactory.getFactory().handle(message);
			
		}catch(Exception e){
			return ;
		}
	}
	
	

	public synchronized static UDPTransmissionManager getInstance() {
		if (manager == null) {
			manager = new UDPTransmissionManager();
		}
		return manager;

	}
 
	public   void destroy() {
		datagramSocket.disconnect();
		datagramSocket.close();
		executor.shutdownNow();
		manager = null;
	}
}