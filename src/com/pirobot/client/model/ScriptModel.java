package com.pirobot.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import com.alibaba.fastjson.annotation.JSONField;
import com.pirobot.client.handler.message.ScriptCommandMessageHandler;
import com.pirobot.client.network.connector.UDPTransmissionManager;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.UDPBordcastAppender;

public class ScriptModel {

	protected final static LoggerUtils logger = LoggerUtils.getLogger(ScriptModel.class);
    private transient List<ScriptCommand> clonedCommandList = new ArrayList<ScriptCommand>();

	public Script  script ;
	public HashMap<String, String> roles = new  HashMap<String,String>();
	
	public String leader;
	
	private String selfRole;
		
	private volatile ScriptCommand mCurrent;
	private volatile boolean isPaused = false;
	
	private volatile boolean isRuning = false;
	
	public ScriptModel copy()
	{
		ScriptModel scriptModel = new ScriptModel();
		scriptModel.script = this.script;
		scriptModel.roles.putAll(this.roles);
		scriptModel.leader = this.leader;
		scriptModel.selfRole = this.selfRole;
		return scriptModel;
	}
	
	@JSONField(serialize = false)
	public ScriptCommand getFristCommand(){
		ScriptCommand command = null;
		if(!script.getCommandList().isEmpty()){			
			for(int i = 0; i < script.getCommandList().size(); i++){
				ScriptCommand tmpCommand = script.getCommandList().get(i);
				if(0 == tmpCommand.getParent()){
					command = tmpCommand;
					break;
				}
			}
		}
		return command;
	}
	public void runing(){
		isRuning = true;
	}
	public boolean isRuning(){
		return isRuning;
	}
	public void runend(){
		isRuning = false;
	}
	
	public void pause(){
		isPaused = true;
		RobotWrapper.getInstance().getRobotActionIntegration().notifyScriptPause();
	}
	public boolean isPaused(){
		return isPaused;
	}
	public void resume(){
		isPaused = false;
		RobotWrapper.getInstance().getRobotActionIntegration().notifyScriptStart();
	}
	
	synchronized public void setCurrent(ScriptCommand obj){
		mCurrent = obj;
	}
	
	public ScriptCommand getCurrent(){
		return mCurrent;
	}
	
	@JSONField(serialize = false)
	public Script getScript() {
		return script;
	}
	
	public void cloneCommandList(){
		clonedCommandList.clear();
		clonedCommandList.addAll(script.getCommandList());
	}
	
	public void  removeCloned(ScriptCommand obj){
		clonedCommandList.remove(obj);
	}

	public void  clearCloned(){
		clonedCommandList.clear();
	}
	
	public void setSelfRole(String selfRole) {
		this.selfRole = selfRole;
	}
	
	public List<String> getOtherRole()
	{
		Set<String> roleSet = new HashSet<String>();
		if(script == null || script.getCommandList() == null)
			return new ArrayList<String>();;
		for(ScriptCommand command : script.getCommandList()){
			String role = command.getRole();
			if(!this.selfRole.equals(role))
				roleSet.add(role);
		}
		return new ArrayList<String>(roleSet);
	}
	
	@JSONField(serialize = false)
	public void setScript(Script script) {
		this.script = script;
	}

	@JSONField(serialize = false)
	public ScriptCommand getCommand(long index){
		for(ScriptCommand command:script.getCommandList()){
			if(index == command.getSort()){
				return command;
			}
		}
		
		return null;
	}
	
	public List<String> getOtherDeviceIds(){
		
		List<String> list = new ArrayList<String>();
		list.addAll(roles.values());
		list.remove(RobotWrapper.getInstance().getDeviceId());
		return list;
	}
	
	@JSONField(serialize = false)
	public List<ScriptCommand> getSelfCommandList(long parent){
		List<ScriptCommand> list = new ArrayList<ScriptCommand>();
		for(int i = 0 ; i<script.getCommandList().size();i++){
			ScriptCommand command = script.getCommandList().get(i);
			if(parent == command.getParent() && isSelf(command)){
				list.add(command);
				UDPTransmissionManager.getInstance().send(UDPBordcastAppender.LOGMARK + "剧本【" + Thread.currentThread().getId() + "：" + RobotWrapper.getInstance().getName() + "】【角色：" + command.getRole() + "】【父节点】：" + parent + "-->【子节点】：" + command.getSort());
			}
		}
		
		return list;
	}
	
	@JSONField(serialize = false)
	public List<ScriptCommand> getSelfCommandList(String action){
		List<ScriptCommand> list = new ArrayList<ScriptCommand>();
		for(int i = 0 ; i<script.getCommandList().size();i++){
			ScriptCommand command = script.getCommandList().get(i);
			if(action.equals(command.getAction()) && isSelf(command)){
				list.add(command);
			}
		}
		
		return list;
	}


	@JSONField(serialize = false)
	public List<ScriptCommand> getOtherCommandList(long parent){
		List<ScriptCommand> list = new ArrayList<ScriptCommand>();
		for(int i = 0 ; i<script.getCommandList().size();i++){
			ScriptCommand command = script.getCommandList().get(i);
			if(parent == command.getParent() && !isSelf(command) ){
				list.add(command);
			}
		}
		
		return list;
	}
	
	
	public String getSelfRole(){
		if(selfRole!=null){
			return selfRole;
		}
		
		String deviceId = RobotWrapper.getInstance().getDeviceId();
		for(String key:roles.keySet()){
			if(deviceId.equals(roles.get(key))){
				selfRole = key;
				break;
			}
		}
		
		return selfRole;
	}
	
	@JSONField(serialize = false)
	public String getLeaderId(){
		if(leader == null)
		{
			leader = clonedCommandList.get(0).getRole();
		}
		return roles.get(leader);
	}
	
	 
	
	@JSONField(serialize = false)
	public boolean isSelfFrist(){
		ScriptCommand command = null;
		if(!script.getCommandList().isEmpty()){			
			for(int i = 0; i < script.getCommandList().size(); i++){
				ScriptCommand tmpCommand = script.getCommandList().get(i);
				if(0 == tmpCommand.getParent()){
					command = tmpCommand;
					break;
				}
			}
		}
		return (command != null && getSelfRole().equals(command.getRole()));
	}
	
	@JSONField(serialize = false)
	public boolean isSelf(ScriptCommand command){
		return (getSelfRole().equals(command.getRole()));
	}
	
	@JSONField(serialize = false)
	public String getDeviceId(String role){
		return roles.get(role);
	}
	
	
	
	
	public boolean hasChildren(long sort,String role){
		for(ScriptCommand command:script.getCommandList()){
			if(sort  == command.getParent() && command.equals(role)){
				return true;
			}
		}
		
		return false;
	}
	
	
	@JSONField(serialize = false)
	public boolean isLastCommand(){
			
		return clonedCommandList.isEmpty();
	}
 
	@JSONField(serialize = false)
	public String getTitle() {
		return script.getTitle();
	}

	public void removeCloned(int sort) {
		// TODO Auto-generated method stub
		removeCloned(getCommand(sort));
	}
	
}
