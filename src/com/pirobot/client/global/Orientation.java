package com.pirobot.client.global;

import java.util.HashMap;
import java.util.Map;

public enum Orientation {
	// forward,backward,left,right:前后左右移动时用到，left,right,up,down：头部左右上下转动时用到，clockwise,anticlockwise：转圈时用到
	forward,backward,left,right,up,down,clockwise,anticlockwise,stop;   
	// Implementing a fromString method on an enum type
    private static final Map<String, Orientation> stringToEnum = new HashMap<String, Orientation>();
    static {
        // Initialize map from constant name to enum constant
        for(Orientation orientation : values()) {
            stringToEnum.put(orientation.toString(), orientation);
        }
    }
    
    // Returns Blah for string, or null if string is invalid
    public static Orientation fromString(String symbol) {
        return stringToEnum.get(symbol);
    }
}
