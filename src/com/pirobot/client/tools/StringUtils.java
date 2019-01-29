package com.pirobot.client.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pirobot.client.global.Constants;

public class StringUtils {
	static String[] units = {"十", "百", "千", "万"};
	static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
	public static double compareEnglishValue(String criterion,String answer){
		criterion = criterion.trim().toLowerCase().replaceAll("[\\pP\\p{Punct}]+", "");
		answer = answer.trim().toLowerCase().replaceAll("[\\pP\\p{Punct}]+", "");
		String[] expectedWords = criterion.split(" ");
		String[] listenedWords = answer.split(" ");
		int expectedWordNum = expectedWords.length;
		int matchedWordNum = 0;
		for(String listenedWord : listenedWords)
		{
			if(criterion.contains(listenedWord))
			{
				matchedWordNum++;
				criterion = criterion.replaceFirst(listenedWord, "");
			}
		}
		double value = (matchedWordNum * 1.0 / expectedWordNum) * (1 - Math.abs(matchedWordNum - expectedWordNum)  * 1.0 / expectedWordNum);
		
		return value;
	}
  
	
	 
	
	public static boolean compareEnglish(String criterion,String answer){
		return compareEnglishValue(criterion,answer) >=  Constants.ENGLISH_EQUAL_DEGREE;
	}
  
	public static boolean isEnglish(String answer){
		answer = answer.replaceAll("[\\pP\\p{Punct}\\s]+", "");
		return answer.matches("^[a-zA-Z]*");
	}
	
	public static boolean hasEnglish(String answer){
		answer = answer.replaceAll("[\\pP\\p{Punct}\\s]+", "");
		Pattern pattern = Pattern.compile("[a-zA-Z]");
		return pattern.matcher(answer).find();
	}
	
	public static List<String> getEnglishSentence(String sentence)
	{
		 List<String> partSentence = new ArrayList<String>();
		    String regexEn = "([a-zA-Z][a-zA-Z\\s\\pP\\p{Punct}]+)";
		    Pattern pEn = Pattern.compile(regexEn);
		    Matcher mEn = pEn.matcher(sentence);
		    int lastIndex = 0;
		    while(mEn.find()) {
		        String matchedEnSentence = mEn.group(1);
		        if(!matchedEnSentence.trim().isEmpty())
		        {
		            int index = sentence.indexOf(matchedEnSentence, lastIndex);
		            if(index >= 0)
		            {
		                if(index > lastIndex)
		                    partSentence.add(sentence.substring(lastIndex, index));
		                partSentence.add(matchedEnSentence);
		                lastIndex = index + matchedEnSentence.length();
		            }
		        }
		    }
		    if(lastIndex < sentence.length())
		    {
		        partSentence.add(sentence.substring(lastIndex));
		    }
		    return partSentence;
	}
	
	
	public static String format(int num){
		 StringBuffer buffer = new StringBuffer();
		 if(num<10 && num>=0){
			 return String.valueOf(numArray[num]);
		 }
		 if(num==10){
			 return (units[0]);
		 }
		 if(num==100){
			 return String.valueOf(numArray[1]) + units[1];
		 }
		 if(num>10 && num<100){
			
			 
			 int ten = num/10;
			 int s = num %10;
			 if(ten>1){
				 buffer.append(numArray[ten]);
			 }
			 buffer.append(units[0]);
			 
			 if(s>0)
			 {
				 buffer.append(numArray[s]);	 
			 }
		 }
		 return buffer.toString();
	}
	
	public static String getDatetime(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return dateFormat.format(new Date());
	}
	
	public static String convertIntegerToStr(String number)
	{
		String[] units = new String[] {"十", "百", "千", "万", "十", "百", "千", "亿"};  
        String[] numeric = new String[] {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};  
          
        String result = "";  
          
        // 遍历一行中所有数字  
        for (int k = -1; number.length() > 0; k++)  
        {  
            // 解析最后一位  
            int j = Integer.parseInt(number.substring(number.length() - 1, number.length()));  
            String rnumber = numeric[j];  
              
            // 数值不是0且不是个位 或者是万位或者是亿位 则去取单位  
            if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7)  
            {  
                rnumber += units[k % 8];  
            }  
              
            // 拼在之前的前面  
            result = rnumber + result;  
              
            // 去除最后一位  
            number = number.substring(0, number.length() - 1);  
        }  
          
        // 去除后面连续的零零..  
        while (result.endsWith(numeric[0]))  
        {  
        result = result.substring(0, result.lastIndexOf(numeric[0]));  
        }  
          
        // 将零零替换成零  
        while (result.indexOf(numeric[0] + numeric[0]) != -1)  
        {  
        result = result.replaceAll(numeric[0] + numeric[0], numeric[0]);  
        }  
          
        // 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零  
        for (int m = 1; m < units.length; m++)  
        {  
        result = result.replaceAll(numeric[0] + units[m], units[m]);  
        }  
        return result;
	}
	public static String convertDigitToStr(String digit)
	{
		String[] numeric = new String[] {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"}; 
		String result = "";  
		for (int i = 0; i < digit.length(); i++)  
		 {  
			if(digit.charAt(i) != '.')
			{
			    int number = Integer.parseInt(digit.charAt(i) + "");
			    result += numeric[number];
			}
			else
				result += "点";
		} 
		return result;
	}
	public static String convertNumToStr(String number)
	{
	    String[] numberParts = number.split("\\.");
	    if(numberParts.length == 1)
	    {
	        return convertIntegerToStr(numberParts[0]);
	    }
	    if(numberParts.length == 2)
	    {
	        return convertIntegerToStr(numberParts[0]) + "点" + convertDigitToStr(numberParts[1]);
	    }
	    return "";
	}
	
	public static boolean isYearNum(String text, String number)
	{
		// 是否表示年份
		int fromIndex = text.indexOf(number);
		if(text.length() > fromIndex + number.length() && text.charAt(fromIndex + number.length()) == '年')
		{
			return true;
		}
		return false;
	}
	
	public static boolean isIpAddr(String number)
	{
		return number.split("\\.").length == 4;
	}
	
	public static String convertNumToText(String text){
		String regexEn = "([\\d|\\.]*)";
		Pattern pEn = Pattern.compile(regexEn);
		Matcher mEn = pEn.matcher(text);
		while(mEn.find()) {
		     String number = mEn.group(1);
		     boolean isYearNum = isYearNum(text, number);
		     boolean isIpAddr = isIpAddr(number);
		     if(!isYearNum && !isIpAddr)
		    	 text =  text.replaceFirst(number, convertNumToStr(number));
		     else
		    	 text =  text.replaceFirst(number, convertDigitToStr(number));
		}
		return text;
	}
	public static String clearNotChinese(String buff){
		String tmpString =buff.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");//去掉所有中英文符号
		return tmpString;
	}
}
