package com.pirobot.client.global;

import java.util.HashMap;
import java.util.Map;

public enum LoggerLevel {
	// forward,backward,left,right:前后左右移动时用到，left,right,up,down：头部左右上下转动时用到，clockwise,anticlockwise：转圈时用到
	debug, info, warn, error;   
	// Implementing a fromString method on an enum type
    private static final Map<String, LoggerLevel> stringToEnum = new HashMap<String, LoggerLevel>();
    static {
        // Initialize map from constant name to enum constant
        for(LoggerLevel orientation : values()) {
            stringToEnum.put(orientation.toString(), orientation);
        }
    }
    
    // Returns Blah for string, or null if string is invalid
    public static LoggerLevel fromString(String symbol) {
        return stringToEnum.get(symbol);
    }
}
