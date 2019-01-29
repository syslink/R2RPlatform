package com.pirobot.client.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.client.driver.Protocol;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.model.Member;

import com.pirobot.client.tools.Pair;

public class TeamMemberManager {
	private ConcurrentHashMap<String, Member> memberMap = new ConcurrentHashMap<String, Member>();
	private BlockingQueue<JSONObject> leaderMoveActionQueue = new ArrayBlockingQueue<JSONObject>(100);
	private BlockingQueue<JSONObject> selfMoveActionQueue = new ArrayBlockingQueue<JSONObject>(100);
	private ConcurrentHashMap<String, List<Pair<String, Long>>> memberActionMap = new ConcurrentHashMap<String, List<Pair<String, Long>>>(); 
	private static TeamMemberManager manager = null;
	private String leaderId = "";
	private TeamMemberManager(){
		Member self = new Member(RobotWrapper.getInstance().getDeviceId(), false);
		memberMap.put(self.getId(), self);
		checkMemberIsAlive();
		MemberHeartBeatMessage.loopSendHB();
	}
	
	public static TeamMemberManager getInstance(){
		if(manager == null)
			manager = new TeamMemberManager();
		return manager;
	}
	
	public void checkMemberIsAlive()
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new Runnable() {
			public void run() {
				try{
					while(true)
					{
						Thread.sleep(90000);
						long curTime = System.currentTimeMillis();
						if(memberMap.size() <= 1)
							continue;
						List<Member> memberList = new ArrayList<Member>(memberMap.values());
						for(Member m : memberList)
						{
							if(!m.getId().equals(RobotWrapper.getInstance().getDeviceId()) && curTime - m.getHeartBeatTime() > 90000)
								memberMap.remove(m.getId());
						}
					}
				}catch(Exception e){}
			}
		});
	}
	public void addMember(String memberId)
	{
		memberMap.put(memberId, new Member(memberId, false));
	}

	public List<Member> getMemberList(){
		return new ArrayList<Member>(memberMap.values());
	}
	
	public Member getSelfInfo()
	{
		return memberMap.get(RobotWrapper.getInstance().getDeviceId());
	}
	public void updateMemberHBTime(String memberId)
	{
		if(memberMap.containsKey(memberId))
			memberMap.get(memberId).setHeartBeatTime(System.currentTimeMillis());
	}
	public void setLeader(String leaderId)
	{
		if(!this.leaderId.equals(leaderId))
		{
			if(!this.leaderId.isEmpty() && memberMap.containsKey(leaderId))
				memberMap.get(leaderId).setLeader(false);
				
			this.leaderId = leaderId;
			if(memberMap.containsKey(leaderId))
			{
				memberMap.get(leaderId).setLeader(true);
			}
			else
			{
				memberMap.put(leaderId, new Member(leaderId, true));
			}
		}
	}
	public Member getLeader()
	{
		return memberMap.get(leaderId);
	}
	
	public Member getMember(String id)
	{
		return memberMap.get(id);
	}
	
	public Member getMemberAnyway(String id)
	{
		Member m = memberMap.get(id);
		if(m == null)
		{
			memberMap.put(id, new Member(id, false));
		}
		return memberMap.get(id);
	}		

	public void addLeaderMoveAction(String actionInfo)
	{
		JSONObject actionObj = JSON.parseObject(actionInfo);
		String action = actionObj.getString("action");
		if(isRobotAction(action))
		{
			if(!leaderMoveActionQueue.offer(actionObj))
			{
				leaderMoveActionQueue.poll();
				leaderMoveActionQueue.offer(actionObj);
			}
		}
	}

	public void addMemberMoveAction(String memberId, String actionInfo)
	{
		JSONObject actionObj = JSON.parseObject(actionInfo);
		String action = actionObj.getString("action");
		if(isRobotAction(action))
		{
			int duration = 0;
			if(actionObj.containsKey("duration"))
				duration = Integer.parseInt(actionObj.getString("duration"));
			Long moveTime = System.currentTimeMillis();
			if(!action.equals(Protocol.ACTION_1016))
				moveTime += duration;
			Pair<String, Long> actionTimePair = new Pair<String, Long>(actionInfo, moveTime);
			if(memberActionMap.contains(memberId))
			{
				memberActionMap.get(memberId).add(actionTimePair);
			}
			else
			{
				List<Pair<String, Long>> actionList = new ArrayList<Pair<String, Long>>();
				actionList.add(actionTimePair);
				memberActionMap.put(memberId, actionList);
			}
		}
	}
	
	public void addSelfMoveAction(String actionInfo, int duration)
	{
		JSONObject actionObj = JSON.parseObject(actionInfo);
		String action = actionObj.getString("action");
		// 移动、转身、绕圈、动手、动头
		if(isRobotAction(action))
		{
			if(!selfMoveActionQueue.offer(actionObj))
			{
				selfMoveActionQueue.poll();
				selfMoveActionQueue.offer(actionObj);
			}
		}
	}
	
	public boolean isRobotAction(String actionType)
	{
		return actionType.equals(Protocol.ACTION_1001) || actionType.equals(Protocol.ACTION_1002) 
			|| actionType.equals(Protocol.ACTION_1003) || actionType.equals(Protocol.ACTION_1016) || actionType.equals(Protocol.ACTION_1017);
	}
	public List<Member> getOtherMemberList() {
		Map<String, Member> cpMap = new ConcurrentHashMap<String, Member>();
		cpMap.putAll(memberMap);
		cpMap.remove(RobotWrapper.getInstance().getDeviceId());
		return new ArrayList<Member>(cpMap.values());
	}
	
	public List<Member> getOtherMembersWithoutLeader() {
		Map<String, Member> cpMap = new ConcurrentHashMap<String, Member>();
		memberMap.putAll(cpMap);
		cpMap.remove(leaderId);
		return new ArrayList<Member>(cpMap.values());
	}
	
	public int getAllMemberNum()
	{
		return memberMap.size();
	}
}
