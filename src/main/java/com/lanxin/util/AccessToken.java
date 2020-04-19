package com.lanxin.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AccessToken {

    public static String createToken(String user_id,long timeStamp){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String originstr=user_id+':'+timeStamp;
        byte[] results = md5.digest(originstr.getBytes());
        String assess_token = Base64.encodeBase64String(results);
        return assess_token;
    }

    public static Boolean verfifyToken(String access_token,String session_id,long timeStamp,RedisUtil redisUtil) {
        if(redisUtil==null) return false;
        boolean isExit = redisUtil.hasKey(access_token);
        Map<Object,Object> author=redisUtil.hmget(access_token);
        if(isExit&&(timeStamp- Long.valueOf(String.valueOf(author.get("timeStamp")))<=30*1000*60)) {
            Map<String,Object> Authentication =new HashMap<String,Object>();
            Authentication.put("sessionId", session_id);
            Authentication.put("timeStamp", timeStamp);
            Authentication.put("user_id",author.get("sessionId"));
            redisUtil.hmset(access_token, Authentication);
            return true;
        }else {
            return false;
        }
    }

}
