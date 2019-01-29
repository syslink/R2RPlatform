/**
 *  
 * @author 1968877693
 */
package com.pirobot.client.model;

import java.util.Hashtable;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/** 
 * This class represents the basic user object.
 *
 */
public class ScriptCommand  {

    
	private String cid;
	
	private String sid;
	
	
	private long sort;

	private long  parent;

    private long prepareTime;
    
   	private long delayTime;
    
    private String role;
    
    private String action;
    
    private String content;
    
    
    private Hashtable<String,String> map = new Hashtable<String,String>();
    
	public String getCid() {
		return cid;
	}


	
	public String getAction() {
		return action;
	}



	public long getPrepareTime() {
		return prepareTime;
	}



	public void setPrepareTime(long prepareTime) {
		this.prepareTime = prepareTime;
	}



	public long getDelayTime() {
		return delayTime;
	}



	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
		map = JSON.parseObject(content, new TypeReference<Hashtable<String,String>>(){}.getType());
	}



	public void setCid(String cid) {
		this.cid = cid;
	}

 
    
	 


	public long getSort() {
		return sort;
	}



	public void setSort(long sort) {
		this.sort = sort;
	}



	public long getParent() {
		return parent;
	}



	public void setParent(long parent) {
		this.parent = parent;
	}

 
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	
	 
	public String getString(String name){
		return map.get(name);
	}
	
	public int getIntValue(String name){
		try{
			return Integer.parseInt(map.get(name));
		}catch(Exception e){
			return 0;
		}
	}
	
	public int getIntValue(String name,int defValue){
		try{
			return Integer.parseInt(map.get(name));
		}catch(Exception e){
			return defValue;
		}
	}
	
	public Boolean getBooleanValue(String name){
		try{
			return Boolean.parseBoolean(map.get(name));
		}catch(Exception e){
			return null;
		}
	}
	
	
	public int hashCode(){
		return (getClass().getName() + cid).hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof ScriptCommand){
			return hashCode() == o.hashCode();
		}
		
		return false;
	}
	public String toString()
	{
		return "" + parent + "->" + sort;
	}
}
