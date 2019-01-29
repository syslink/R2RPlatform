package com.pirobot.client.robot;

import java.util.List;

import com.pirobot.client.tools.Pair;

public interface MouthInterface {
	/**
	* 以指定的发音人、语速、音量和音调让机器人将指定的话说出来
	*@param speakerName  发音人
	*@param content  待合成的语句
	*@param speed  语速
	*@param volume  音量
	*@param pitch  语调
	*@return 能正常说话则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean speak(String speakerName, String content, int speed, int volume, int pitch);
	/**
	* 以默认或已设置好的发音人、语速、音量和音调让机器人将指定的话说出来
	*@param content  待合成的语句
	*@return 能正常说话则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean speak(String content);
	/**
	* 以指定的中英文发音人、语速、音量和音调让机器人将指定的话按序说出来;
	* 此接口的存在主要是因为目前没有哪个语音合成的声音能同时将中英文长句自然的合成出来，一旦有这样的合成声音，也就不需要此接口了
	*@param chSpeakerName  中文发音人
	*@param enSpeakerName  英文发音人
	*@param sentenceList  待合成的语句序列，其中Pair<String, Boolean>中第一个参数是待合成的语句，第二个参数表示此句是否为中文
	*@param speed  语速
	*@param volume  音量
	*@param pitch  语调
	*@return 能正常说话则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean blendSpeak(String chSpeakerName, String enSpeakerName, List<Pair<String, Boolean>> sentenceList, int speed, int volume, int pitch);
	
	/**
	* 以默认或已设置好的中英文发音人、语速、音量和音调让机器人将指定的话按序说出来;
	* 此接口的存在主要是因为目前没有哪个语音合成的声音能同时将中英文长句自然的合成出来，一旦有这样的合成声音，也就不需要此接口了
	*@param sentenceList  待合成的语句序列，其中Pair<String, Boolean>中第一个参数是待合成的语句，第二个参数表示此句是否为中文
	*@return 能正常说话则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean blendSpeak(List<Pair<String, Boolean>> sentenceList);

	/**
	* 让机器人停止说话;
	*@return 能正常停止说话则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void stopSpeak();
	
	/**
	* 播放声音文件
	*@param filePath  待播放的文件的url链接
	*@return 能正常播放则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public boolean play(String fileUrl);
	
	/**
	* 停止播放声音文件
	*@return 能正常播放则返回true，否则返回false
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-02-19 12:31:00
	*/
	public void stopPlay();
}
