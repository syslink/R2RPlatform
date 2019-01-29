package com.pirobot.client.handler.script;


import java.io.File;


import com.pirobot.client.global.Constants;
import com.pirobot.client.model.ScriptCommand;
import com.pirobot.client.robot.RobotWrapper;
import com.pirobot.client.tools.CommonUtils;
import com.pirobot.client.tools.OssUtils;

public class VoiceFileHandler implements ScriptCommandHandler {
 
	@Override
	public boolean handle(ScriptCommand data) {
		final String file = data.getString(Constants.Action.FILE);
		String path = String.format(OssUtils.MUSIC_FILE_URL, RobotWrapper.getInstance().getUid(), file);		
		CommonUtils.play(path);
		return false;
	}
}
