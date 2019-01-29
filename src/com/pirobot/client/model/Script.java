/**
 *  
 * @author 1968877693
 */
package com.pirobot.client.model;

import java.util.List;

/** 
 *
 */
public class Script   {
	

	private String sid;

    private String uid;
    
    private String type;

    private String description;
    
    private String title;
    
    private int roleCount;
    
    private List<ScriptCommand> commandList;
    
    
	public String getSid() {
		return sid;
	}
    

	public List<ScriptCommand> getCommandList() {
		return commandList;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUid() {
		return uid;
	}

	
 
	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public int getRoleCount() {
		return roleCount;
	}


	public void setRoleCount(int roleCount) {
		this.roleCount = roleCount;
	}


	public void setCommandList(List<ScriptCommand> commandList) {
		this.commandList = commandList;
		placeFirstCmdOnHead();
	}

	private void placeFirstCmdOnHead()
	{		
		for(ScriptCommand cmd : this.commandList)
		{
			if(cmd.getParent() == 0)
			{
				this.commandList.remove(cmd);
				this.commandList.add(0, cmd);
				break;
			}
		}
	}
   
}
