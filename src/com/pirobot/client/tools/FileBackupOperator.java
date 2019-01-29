package com.pirobot.client.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.pirobot.client.global.Constants;
import com.pirobot.client.robot.RobotWrapper;

public class FileBackupOperator {
	/**
	* 保存语音合成文件，下次无需再次合成便可直接在本地使用，语音文件同时会保存在本地tts目录和云端
	*@param fileId  文件id，值可以为：MD5(语音合成的内容+发音人+语音+语速+语调)，以便唯一确定某次语音合成
	**@param voiceFile 语音合成文件
	*@return 无
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-05-9 12:31:00
	*/
	public static void saveVoiceFile(final String fileId, final File voiceFile)
	{
		if(!voiceFile.exists())
			return;
		// 保存到本地tts目录
		try{
			File savedFile = new File(Constants.CacheDir.tts, fileId);
			if(savedFile.exists())
			{
				savedFile.delete();
			}
			InputStream inStream = new FileInputStream(voiceFile); //读入原文件   
            FileOutputStream fs = new FileOutputStream(savedFile);   
            byte[] buffer = new byte[1024];   
            int byteRead = 0;
            while ((byteRead = inStream.read(buffer)) != -1) {   
                fs.write(buffer, 0, byteRead);   
            }   
            inStream.close();
            fs.close(); 
		}catch(Exception e){
			
		}                     
	
		// 上传到云端保存
		new Thread(new Runnable(){
			public void run()
			{
				String path = String.format(OssUtils.TTS_FILE_URL, RobotWrapper.getInstance().getUid(), fileId);
				OssUtils.upload(path, voiceFile);
			}
		}).start();
	}
	/**
	* 获取语音合成文件，先查看本地是否有此文件，如有，便返回文件对象，如无，便从云端下载文件，如云端也没有，则返回null
	*@param fileId  文件id，值可以为：MD5(语音合成的内容+发音人+语音+语速+语调)，以便唯一确定某次语音合成
	*@return 语音合成文件
	*@exception  无
	*@author Sam@pirobot.club
	*@Time 2017-05-9 12:31:00
	*/
	public static File getVoiceFile(String fileId)
	{
		File savedFile = new File(Constants.CacheDir.tts, fileId);
		if(savedFile.exists())
		{
			return savedFile;
		}
		boolean success = OssUtils.download(Constants.CacheDir.tts, fileId);
		if(success)
		{
			savedFile = new File(Constants.CacheDir.tts, fileId);
			return savedFile;
		}
		return null;
	}
}
