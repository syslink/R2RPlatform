package com.pirobot.client.model;

import java.util.Hashtable;




public class Action {
    
	public String action;
	Hashtable<String,String> content = new Hashtable<String,String>();
	
	public void put(String name,String value){
		content.put(name, value);
	}
	
	public void setContent(Hashtable<String, String> content) {
		this.content = content;
	}

	public String getString(String name){
		return content.get(name);
	}
	
	public Integer getIntValue(String name){
		try{
			return Integer.parseInt(content.get(name));
		}catch(Exception e){
			return null;
		}
	}
	
	public int getIntValue(String name,int defValue){
		try{
			return Integer.parseInt(content.get(name));
		}catch(Exception e){
			return defValue;
		}
	}
	
	public Boolean getBooleanValue(String name){
		try{
			return Boolean.parseBoolean(content.get(name));
		}catch(Exception e){
			return null;
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Hashtable<String, String> getContent() {
		return content;
	}
	
	
	 
}
