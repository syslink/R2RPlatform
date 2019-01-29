package com.pirobot.client.global;

import java.util.HashMap;
import java.util.Map;

public enum ProxyType {
	// Brain:双向，即订阅者将内容发给代理方，代理方再将结果传回订阅者
	// Ear、Eye:输出，即代理方要将听到、看到的内容输出给订阅者
	// Mouth, Arm, Leg, CombineAction：输入，即订阅者要将信息输入给代理方代为执行
	Brain, Ear, Eye, Mouth, Arm, Leg, CombineAction;   
	// Implementing a fromString method on an enum type
    private static final Map<String, ProxyType> stringToEnum = new HashMap<String, ProxyType>();
    static {
        // Initialize map from constant name to enum constant
        for(ProxyType orientation : values()) {
            stringToEnum.put(orientation.toString(), orientation);
        }
    }
    
    // Returns Blah for string, or null if string is invalid
    public static ProxyType fromString(String symbol) {
        return stringToEnum.get(symbol);
    }
}
