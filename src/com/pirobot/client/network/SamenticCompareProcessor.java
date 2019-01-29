 
package com.pirobot.client.network;
import java.io.IOException;
import java.util.HashMap;



import com.pirobot.client.global.Constants;
import com.pirobot.client.global.Global;
import com.pirobot.client.network.result.SimilarityResult;
import com.pirobot.client.tools.LoggerUtils;
import com.pirobot.client.tools.StringUtils;

 
public class SamenticCompareProcessor  {
	static HashMap<String,String> params = new HashMap<String ,String>();
	protected final static LoggerUtils logger = LoggerUtils.getLogger(SamenticCompareProcessor.class);

	public static boolean compare(String criterion,String answer)  
	{
		if(StringUtils.isEnglish(criterion)){
			return StringUtils.compareEnglish(criterion,answer);
		}
		params.put("criterion",criterion);
		params.put("answer", answer);
		try {
			SimilarityResult result = HttpPostProcessor.syncHttpPost(params,Global.getInternalConfig("SEMANTIC_COMPARE_APIURL"),SimilarityResult.class);
		    if(result.isEqual){
		    	return true;
		    }
		    return result.pinyin >= Constants.PINYIN_EQUAL_DEGREE || result.samentic >= Constants.SEMANTIC_EQUAL_DEGREE;
		} catch (IOException e) {
    		logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	
	 
}
