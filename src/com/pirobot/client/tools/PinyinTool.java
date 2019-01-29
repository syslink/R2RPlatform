package com.pirobot.client.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;



import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinyinTool {
	protected final static LoggerUtils logger = LoggerUtils.getLogger(PinyinTool.class);
	private static Map<String, String> similiarToneMap = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("zh", "z"); put("ch", "c"); put("sh", "s"); 
		put("ang", "an");put("eng", "en");put("ing", "in");
		put("l", "n");put("h", "f");put("p", "b");
	}};
	private static Map<String, String> unicodePinyinMap = new HashMap<String, String>();
	private static Map<String, List<String>> pinyinListMap = new HashMap<String, List<String>>();
	
	public static void init()
	{
		try{
			InputStream input = PinyinTool.class.getClassLoader().getResourceAsStream("resource/unicode_to_hanyu_pinyin.txt");
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(input));
			String line = "";
			while((line = bufReader.readLine()) != null)
			{
				String[] lineInfo = line.split(" ");
				if(lineInfo.length == 2)
				{
					unicodePinyinMap.put(lineInfo[0], lineInfo[1]);
				}
			}
		}catch(Exception e){
			logger.error("拼音初始化失败。", e);
		}
	}
	public static int contain(String oriStr, String containedStr)
	{
		int matchedPos = -1;
		if(containedStr.length() > oriStr.length())
			return matchedPos;
		if(containedStr.length() == oriStr.length() && oriStr.equals(containedStr))
			return 0;
		
		List<String> oriPinyinList = getPinyinList(oriStr);
		List<String> containedPinyinList = getPinyinList(containedStr);
		
		int len = oriPinyinList.size();
		int comparedLen = containedPinyinList.size();
		for(int i = 0; i < len; i++)
		{
			int curPos = i;
			int j = 0;
			for(; j < comparedLen; j++)
			{
				String oriPinyin = oriPinyinList.get(curPos);
				if(!oriPinyin.equals(containedPinyinList.get(j)))
					break;
				curPos++;				
			}
			if(j == comparedLen)
			{
				matchedPos = i;
				break;
			}
		}
		return matchedPos;
	}
	public static int robustContain(String oriStr, String containedStr, double threshold)
	{
		oriStr = StringUtils.clearNotChinese(oriStr);
		containedStr = StringUtils.clearNotChinese(containedStr);
		int matchedPos = -1;
		if(containedStr.length() > oriStr.length())
			return matchedPos;
		if(containedStr.length() == oriStr.length() && oriStr.equals(containedStr))
			return 0;
				
		int len = oriStr.length();
		int comparedLen = containedStr.length();
		for(int i = 0; i < len; i++)
		{
			if(i + comparedLen <= len)
			{
				String subStr = oriStr.substring(i, i + comparedLen);
				double similiarity = PinyinProcesser.getInstance().getSimiliarity(subStr, containedStr);
				if(Math.abs(similiarity - threshold) < 0.000001)
				{
					matchedPos = i;
					break;
				}
			}
		}
		return matchedPos;
	}
	public static boolean contain(String oriStr, String[] containedStrList)
	{
		for(String containedStr : containedStrList)
		{
			if(contain(oriStr, containedStr) > -1)
				return true;
		}
		return false;
	}
	public static List<Pinyin> convertToPinyinList(String chinese)
	{
		List<Pinyin> pinyins = new ArrayList<Pinyin>();
		List<String> pinyinList = getPinyinList(chinese);
		for(String pinyin : pinyinList)
		{
			pinyins.add(new Pinyin(pinyin.substring(0, 1), pinyin.substring(1)));
		}
		return pinyins;
	}
	private static List<String> getPinyinList(String chinese)
	{
		if(pinyinListMap.containsKey(chinese))
			return pinyinListMap.get(chinese);
		List<String> pinyinList = new ArrayList<String>();  
        char[] nameChar = chinese.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                	String unicode = Integer.toHexString(nameChar[i]).toUpperCase();
                	if(!unicodePinyinMap.containsKey(unicode))
                		continue;
                	String[] pinyinArr = unicodePinyinMap.get(unicode).split(",");
                	if(pinyinArr == null)
                		continue;
                    String pinyin = pinyinArr[0].substring(0, pinyinArr[0].length() - 1); 
                    Iterator<Entry<String, String>> iter = similiarToneMap.entrySet().iterator();
            		while(iter.hasNext())
            		{
            			Map.Entry<String, String> entry = iter.next();
            			if(pinyin.contains(entry.getKey()))
            			{
            				pinyin = pinyin.replace(entry.getKey(), entry.getValue());
            			}
            		}
            		pinyinList.add(pinyin);
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }else{  
            	pinyinList.add(nameChar[i] + "");  
            }  
        }  
        pinyinListMap.put(chinese, pinyinList);
        return pinyinList; 
	}
	
	public static void main(String[] args)
	{
		PinyinTool.init();
		System.out.println(PinyinTool.robustContain("起床了小黄", "小黄", 0.75));
	}
}

