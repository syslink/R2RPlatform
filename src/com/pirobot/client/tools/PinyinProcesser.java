package com.pirobot.client.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PinyinProcesser {
	private Map<String, String> similiarToneMap = new HashMap<String, String>(){{
		put("zh", "z"); put("ch", "c"); put("sh", "s"); 
		put("ang", "an");put("eng", "en");put("ing", "in");
		put("n", "l");put("h", "f");put("l", "r");put("p", "b");
	}};
	private ConcurrentHashMap<String, List<Pinyin>> historyPinyinInfoMap = new ConcurrentHashMap<String, List<Pinyin>>();
	private static PinyinProcesser pinyinProcesser = null;
	private PinyinProcesser(){}
	public static PinyinProcesser getInstance()
	{
		if(pinyinProcesser == null)
			pinyinProcesser = new PinyinProcesser();
		return pinyinProcesser;
	}
	
	public double getSimiliarity(String firstSentence, String secondSentence)
	{
		double similiarity = 0.0f;
		List<Pinyin> firstPinyinList = PinyinTool.convertToPinyinList(firstSentence);
		List<Pinyin> secondPinyinList = PinyinTool.convertToPinyinList(secondSentence);
		
		int firstLen = firstPinyinList.size();
		int secondLen = secondPinyinList.size();

		double integration = calculateTwoPinyinList(firstPinyinList, secondPinyinList);
		double baseLen = firstLen > secondLen ? firstLen : secondLen * 1.0;
		similiarity = integration / baseLen;
		return similiarity;
	}
	public List<Double> getSimiliarityList(String firstSentence, List<String> sentenceList)
	{
		List<Double> similiarityList = new ArrayList<Double>();
		for(String sentence : sentenceList)
		{
			double similiarity = getSimiliarity(firstSentence, sentence);
			similiarityList.add(similiarity);
		}
		return similiarityList;
	}
	private double calculateTwoPinyinList(List<Pinyin> firstNormalizedPinyinList, List<Pinyin> secondNormalizedPinyinList)
	{
		double totalIntegration = 0.0f;
		for (Pinyin firstPiniyin : firstNormalizedPinyinList) {
			double maxIntegration = 0.0f;
			Pinyin matchedPinyin = null;
			for (Pinyin secondPinyin : secondNormalizedPinyinList) {
				double integration = calculateTwoPinyin(firstPiniyin, secondPinyin);
				if(integration > maxIntegration)
				{
					maxIntegration = integration;
					matchedPinyin = secondPinyin;
				}
			}
			totalIntegration += maxIntegration;
			if(maxIntegration > 0.7)
				secondNormalizedPinyinList.remove(matchedPinyin);
		}
		return totalIntegration;
	}
	private double calculateTwoPinyin(Pinyin firstPinyin, Pinyin secondPinyin)
	{
		double shengmuValue = firstPinyin.getShengmu().equals(secondPinyin.getShengmu()) ? 0.5 : 0.0f;
		double yunmuValue = firstPinyin.getYunmu().equals(secondPinyin.getYunmu()) ? 0.5f : 0.0f;
		return shengmuValue + yunmuValue;
	}
	public static void main(String[] args)
	{
		System.out.println(PinyinProcesser.getInstance().getSimiliarity("二", "三"));
	}
}
