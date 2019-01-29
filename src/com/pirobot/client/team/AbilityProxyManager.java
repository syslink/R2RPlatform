package com.pirobot.client.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pirobot.client.global.ProxyType;
import com.pirobot.client.robot.RobotWrapper;

public class AbilityProxyManager {
	private Map<ProxyType, Set<String>> proxyRobotMap = new HashMap<ProxyType, Set<String>>();
	private Map<ProxyType, Set<String>> subscriberRobotMap = new HashMap<ProxyType, Set<String>>();
	
	private static AbilityProxyManager instance = null;
	private AbilityProxyManager(){}
	public static AbilityProxyManager getInstance()
	{
		if(instance == null)
		{
			instance = new AbilityProxyManager();
		}
		return instance;
	}
	public void addProxy(String proxyId, ProxyType proxyType)
	{
		if(RobotWrapper.getInstance().getDeviceId().equals(proxyId))
			return;
		if(proxyRobotMap.containsKey(proxyType))
		{
			proxyRobotMap.get(proxyType).add(proxyId);
		}
		else
		{
			Set<String> proxyRobotSet = new HashSet<String>();
			proxyRobotSet.add(proxyId);
			proxyRobotMap.put(proxyType, proxyRobotSet);
		}
	}
	public void deleteProxy(String proxyId, ProxyType proxyType)
	{
		if(RobotWrapper.getInstance().getDeviceId().equals(proxyId))
			return;
		if(proxyRobotMap.containsKey(proxyType))
		{
			proxyRobotMap.get(proxyType).remove(proxyId);
		}
	}
	public void addSubscriber(String subscriberId, ProxyType proxyType)
	{
		if(RobotWrapper.getInstance().getDeviceId().equals(subscriberId))
			return;
		if(subscriberRobotMap.containsKey(proxyType))
		{
			subscriberRobotMap.get(proxyType).add(subscriberId);
		}
		else
		{
			Set<String> subscriberRobotSet = new HashSet<String>();
			subscriberRobotSet.add(subscriberId);
			subscriberRobotMap.put(proxyType, subscriberRobotSet);
		}
	}
	public void deleteSubscriber(String subscriberId, ProxyType proxyType)
	{
		if(subscriberRobotMap.containsKey(proxyType))
		{
			subscriberRobotMap.get(proxyType).remove(subscriberId);
		}
	}
	public Set<String> getProxyRobots(ProxyType proxyType)
	{
		if(proxyRobotMap.containsKey(proxyType))
		{
			return proxyRobotMap.get(proxyType);
		}
		return new HashSet<String>();
	}

	public Set<String> getSubscriberRobots(ProxyType proxyType)
	{
		if(subscriberRobotMap.containsKey(proxyType))
		{
			return subscriberRobotMap.get(proxyType);
		}
		return new HashSet<String>();
	}
	
}
