package com.pirobot.client.global;

import java.util.HashMap;
import java.util.Map;

public enum ParserType {
	// parse:解析语句，获得结构化信息，
	// addName,getNames,clearNames,deleteName：添加人名、获取所有人名、清除所有人名、删除某个人名
	parse,addName,getNames,clearNames,deleteName;   
	// Implementing a fromString method on an enum type
    private static final Map<String, ParserType> stringToEnum = new HashMap<String, ParserType>();
    static {
        // Initialize map from constant name to enum constant
        for(ParserType orientation : values()) {
            stringToEnum.put(orientation.toString(), orientation);
        }
    }
    
    // Returns Blah for string, or null if string is invalid
    public static ParserType fromString(String symbol) {
        return stringToEnum.get(symbol);
    }
}
