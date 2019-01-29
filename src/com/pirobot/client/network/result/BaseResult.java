package com.pirobot.client.network.result;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BaseResult {
	public int code = 200;
	public Object data;
	public List<HashMap<String,?>> result;
	
	public HashMap<String,?> getRandom(){
		
		return result.get(new Random().nextInt(result.size()));
	}
}
