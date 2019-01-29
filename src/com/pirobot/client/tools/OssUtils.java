package com.pirobot.client.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;



import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

public class OssUtils {
	final static String OSS_ACCESS_KEY = "AD5AgZdRPH411hf2SjLVo3lPjoBrIS";
	final static String OSS_ACCESS_ID = "mX5GgMHT7fJ3EGCl";
	final static String OSS_BUCKET_NAME = "doulaig";
	final static String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com"; 
	public final static String FILE_URL = "http://"+OSS_BUCKET_NAME+".oss-cn-hangzhou.aliyuncs.com/%s"; 
	public final static String MUSIC_FILE_URL = "http://"+OSS_BUCKET_NAME+".oss-cn-hangzhou.aliyuncs.com/music/%s/%s"; 
	public final static String TTS_FILE_URL = "http://"+OSS_BUCKET_NAME+".oss-cn-hangzhou.aliyuncs.com/tts/%s/%s"; 

	protected final static LoggerUtils logger = LoggerUtils.getLogger(OssUtils.class);

    public static void delete(String key){
    	OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
		client.deleteObject(OSS_BUCKET_NAME, key);
		client.shutdown();
	}
    
	public static void upload(String key,File file){
		OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
		client.putObject(OSS_BUCKET_NAME, key, file);
		client.shutdown();
	}
 
	public static void upload(String key,InputStream stream){
		OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
		client.putObject(OSS_BUCKET_NAME, key, stream);
		client.shutdown();
	}
	
    public static boolean download(String path,String key){
    	boolean  success = false;
    	try{
    		OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
        	if(client.doesObjectExist(OSS_BUCKET_NAME, key)){
        		File target = new File(path,key);
            	client.getObject(new GetObjectRequest(OSS_BUCKET_NAME, key),target);
        		logger.info("OSS下载成功:"+target.getAbsolutePath());
        		
        		createFlagFileQuietly(path,key);
        		
        		success = true;
                
        	}
        	
    		client.shutdown();	

    	}catch(Exception e){
    		logger.error(e.getMessage(), e);
    	}
    	
        return success;
	}
    
    private static void createFlagFileQuietly(String  path,String key){
    	File file = new File(path,key+".flag");
        if(!file.exists()){
        	try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }

    }

    
    public static long downloadAllInOSSFolder(String localPath, String folderName){
    	long totalSize = 0;
    	OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
    	ObjectListing listing = client.listObjects(OSS_BUCKET_NAME, folderName);
    	for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
    		String key = objectSummary.getKey();
    		if(key.equals(folderName + "/"))
    			continue;
    		long size = objectSummary.getSize();
    		totalSize += size;
    		
    		File target = new File(localPath, key);
        	client.getObject(new GetObjectRequest(OSS_BUCKET_NAME, key), target);
    	}
		client.shutdown();
		return totalSize;
	}
    
    
    /**
     * 
     * @return返回下载速度  kb/秒
     */
    public static long testDownloadSpeed(){
    	
    	long time = System.currentTimeMillis();
    	
    	OSSClient client = new OSSClient(ENDPOINT, OSS_ACCESS_ID, OSS_ACCESS_KEY);
    	File target = new File("/opt/startrun/","protobuf-java-3.0.0.jpg");
    	client.getObject(new GetObjectRequest(OSS_BUCKET_NAME, "protobuf-java-3.0.0.jpg"),target);
		client.shutdown();	
		
		long countTime = (System.currentTimeMillis() - time) / 1000;
		long speed = target.length() / countTime / 1000;
		
		return speed;
		
    }

}