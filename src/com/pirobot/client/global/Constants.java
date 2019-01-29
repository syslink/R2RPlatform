 
package com.pirobot.client.global;

public interface Constants {

	public interface Action{
		 String CONTENT = "content";
		 String STATUS = "status";
		 String MOVE = "LegMove";
		 String ORIENTATION = "orientation";
		 String DISTANCE = "distance";
		 String DELAY = "delay";
		 String ANGLE = "angle";
		 String FASTER = "faster";
		 String LARGEN = "largen";
		 String SPEATER = "speaker";
		 String LOCAL_PERSON_NAME = "localPersonName";
		 String DELAY_TIME = "delayTime";
		 String PREPARE_TIME = "prepareTime";
		 
		 String DURATION = "duration";
		 String NAME = "name";
		 String FILE = "file";
		 String RADIUS = "radius";
		 String EMOTION ="emotion";
		 String EMOTION_DELAY_TIME ="emotionDelayTime";
		 String COMBINEACTIONS ="combineActionsName";
		 String EXTRACONTENT ="extraContent";
		 String COMBINEACTIONS_DELAY_TIME ="combineActionsDelayTime";
		 String DANCE = "danceName";
		 String MUSIC = "music";
		 String LEDCONTROLLER = "ledController";
		 String MOTORCONTROLLER = "motorController";
		 
		 String TEXT = "text";
		 String SPEAKER = "speaker";
		 String QUESTION = "question";
		 String CRITERION = "criterion";
		 String ERRORHINT = "errorhint";
		 String ENSPEAKER = "enspeaker";

		 String COUNT = "count";
		 String FAILTIPS = "failtips";
		 String RIGHTTIPS = "righttips";
		 
		 String REANGLE = "reangle";

		 String VOLUME = "volume";
		 String SPEED = "speed";
		 String TONE = "tone";
		 
		 String LEFT_ANGEL = "leftAngle";
		 String RIGHT_ANGEL = "rightAngle";
		 String TURN_ANGEL = "turnAngle";
		 String MOVE_LIST = "moveList";
		 String HEAD_TURN_LIST = "headTurnList";

		 String POSNAME = "posName";
		 String POSITION = "position";
		 
		 String sid = "sid";		 
		 String roles = "roles";
		 String deviceIdPrefix = "deviceId";
		 
		 String LISTENED_SENTENCE_ID = "listenedSentenceId";
		 String IS_TO_LISTEN_CHINESE = "isToListenChinese";
	}
	
	public interface ScriptAction{
		String ACTION_1  = "1";//讲话
	}
	
	public interface RobotSpeedLimit{
		double maxStraightSpeed = 25.0;  //每秒最快前进25cm
		double minStraightSpeed = 1.0;  //每秒最慢前进1cm
		double maxTurnSpeed = 163.0;  //每秒最快转163度
		double minTurnSpeed = 1.0;  //每秒最慢转1度
	}
	 
	public interface CommandAction{
		 String SPEAK = "1";//讲话
		 String MOVE = "2";//移动
		 String TURN = "3";//转动
		 String EMOTION = "4";//表情
		 String HAND = "5";//手臂动作
		 String QA = "6";//人机问答
	}
 
	public interface Emotion{
		 String SAD = "sad"; 
		 String SMILE = "smile"; 
		 String CRY = "cry"; 
		 String ANGRY = "angry"; 
		 String LOOK = "look"; 
		 String DEFAULT = "default"; 
		 String SPEAKING = "speaking"; 
		 String HEARTBROKEN = "heart_broken";
		 
		 String WIFI_ENABLE = "wifi_enable"; 
		 String WIFI_DISABLE = "wifi_disable"; 
		 String WIFI_ANIM_1 = "wifi_anim_1"; 
		 String WIFI_ANIM_2 = "wifi_anim_2"; 
		 String WIFI_ANIM_3 = "wifi_anim_3"; 
		 String WIFI_ANIM = "wifi_anim"; 

		 
	}
	
	
	public interface MessageFromat{
		 String FROMAT_TXT = "txt";
		 String FROMAT_JSON = "json";
		 String FROMAT_XML = "xml";
	}
	
	public interface MessageAction {

		 // 文本消息内容
		 String ACTION_100 = "100";
		 
		 // 代理机器人想订阅者发送的语音识别内容
		 String ACTION_101 = "101";
		 
		 // 动作类消息内容
		 String ACTION_200 = "200";
		 
		// 让机器人说话的消息
		String ACTION_303 = "303";

		// 让机器人说中英混杂话的消息
		String ACTION_304 = "304";

		// 让机器人停止说话的消息
		String ACTION_305 = "305";
		
		 // 剧本就绪消息
		 String ACTION_900 = "900";
		 // 执行某一步剧本消息
		 String ACTION_901 = "901";
		 
		 // 控制剧本进度消息
		 String ACTION_902 = "902";
		 
		 // 成员向队长发送剧本已收到的消息，队长判断所有成员都已收到剧本，即可开始表演
		 String ACTION_904 = "904";
		 
		 //收到成员位置信息消息
		 String ACTION_401 = "401";
		 
		 //收到队长的动作消息，包括表情、手臂、移动等动作
		 String ACTION_402 = "402";
		 
		 //收到其它队员的自身角度
		 String ACTION_403 = "403";
		 
		 //队长广播自身信息
		 String ACTION_404 = "404";
		 
		 //收到队员的动作消息，包括表情、手臂、移动等动作
		 String ACTION_405 = "405";

		 //新成员加入消息
		 String ACTION_406 = "406";		 

		 //老成员响应新成员加入的信息
		 String ACTION_407 = "407";
		 
		 //成员心跳包
		 String ACTION_408 = "408";

		 //见面打招呼消息
		 String ACTION_409 = "409";
		 
		 //成员说话消息
		 String ACTION_410 = "410";

		 //成员间普通消息
		 String ACTION_411 = "411";

		 //修改成员姓名，此消息是直接从CMP网页端发送过来的（http://rmp.cellbot.cn/cgi/message/send.api）
		 String ACTION_412 = "412";
		 
		 //向周围的成员获取答案
		 String ACTION_413 = "413";
		 
		 //给请教问题的成员提供答案
		 String ACTION_414 = "414";
		 
		 //向其它成员发送自己听到的话
		 String ACTION_415 = "415";
		 
		 //控制机器人肢体动作，如移动、动手、动头
		 String ACTION_700 = "700";
		 
		 //控制机器人手臂上下运动
		 String ACTION_701 = "701";
		 
		 // 控制机器人移动到某个位置
		 String ACTION_702 = "702";

		 // 控制机器人执行联合动作
		 String ACTION_703 = "703";
		 
		 // 能力代理-即某机器人可以将其它机器人的某项能力为我所用，譬如听力不好的机器人，可以将听力好的机器人作为它的助听器
		 String ACTION_920 = "920";
	}

	
	public interface Orientation{
		String FORWARD = "forward";
		String BACKWARD = "backward";
		String LEFT = "left";
		String RIGHT = "right";
		String STOP = "stop";
	}
	
	public interface RobotState{
		 String STATE_QUIET = "quiet";//安静模式，不发出声音
		 String STATE_ACTIVE = "active";//默认模式，正常模式
		 String STATE_RUNING_SCRIPT = "900";
		 String STATE_SPEAKING = "001";
		 String STATE_LISTENING = "002";
		 String STATE_SINGING = "003";
		 String STATE_REPEATING_LEADER = "004";
	}
	
	
	public interface AnswerSource{
		 String CUSTOMQA = "0"; //定制QA
		 String ACTION = "1";//自定义action
		 String XUNFEI = "2";//讯飞
		 String TULING = "3";//图灵
	}
	
	public interface CacheDir{
		String tts = "./pirobot/wavcache/";
		String music = "./pirobot/mp3/";
		String script = "./pirobot/script/";
	}
	
	public int minGapTimeWithTwoAction = 500;
	
	public int DEF_TTS_VALUE = 50;
	public String STATE_PAUSE = "0";
	public String STATE_RESUME = "1";
	public String STATE_CANCEL = "-1";

	public int HTTP_TIMEOUT = 3000;
	
	//数据缓存有效期7天
	public long DATA_LIFE_TIME =7 * 24 * 60 * 60 * 1000;
	
	
	public double PINYIN_EQUAL_DEGREE = 0.9;
	public double SEMANTIC_EQUAL_DEGREE = 0.8;
	
	public double ENGLISH_EQUAL_DEGREE = 0.8;

	
	public byte MESSAGE_SEPARATE = '\n';
	public String CH_JUHAO = "。";
	
	public String LANGUAGE_CN = "zh_cn";
	public String LANGUAGE_EN = "en_us";
	
	String DEF_ENGLISH_SPEAKER= "aiscatherine";
	String DEF_WOMAN_SPEAKER= "xiaoqi";
	String DEF_SPEAKER= "nannan";
	String SYSTEM_UID= "10000";
	
	String WIFI_SUCCESS_RESPONSE = "{\"code\":200}";
	String WIFI_CONNECTING_RESPONSE = "{\"code\":100}";
	String WIFI_ERROR_RESPONSE = "{\"code\":-1,\"message\":\"the ssid or password is incorrect.\"}";
}
