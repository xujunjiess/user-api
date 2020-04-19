package com.lanxin.controller;

import com.alibaba.fastjson.JSONObject;
import com.lanxin.bean.Users;
import com.lanxin.common.ApiResult;
import com.lanxin.service.UsersService;
import com.lanxin.util.AccessToken;
import com.lanxin.util.JwtUtil;
import com.lanxin.util.RSAUtil;
import com.lanxin.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Api(value = "UserController|登录")
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    private String publicKeyStr;

    private String privateKeyStr;

    /**
     * JWT token
     * @param user
     * @return
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public Object login(@RequestBody Users user){
        JSONObject jsonObject=new JSONObject();
        Users users=usersService.getUsers(user);
        if(users==null){
            jsonObject.put("message","登录失败，帐号密码不正确!");
            return jsonObject;
        }
        String accessToken= JwtUtil.sign(users.getUsername(),users.getId().toString());
        //redisTemplate.opsForValue().set("accessToken",accessToken);
        jsonObject.put("accessToken",accessToken);
        jsonObject.put("users",users);
        return jsonObject;
    }

    /**
     * 获取公钥，返回到前端
     * 前端在向后台发起登录请求之前，先请求后台获取公钥的方法
     * 前端引入jsencrypt.min.js文件
     * 通过公钥对用户名和密码加密
     * @return
     * @throws Exception
     */
    @ApiOperation(value="获取公钥", notes="返回到前端")
    @GetMapping(value = "/publicKey")
    @ResponseBody
    public Map<String, Object> getPublicKey() throws Exception {
        KeyPair keyPair=RSAUtil.getKeyPair();
        publicKeyStr=RSAUtil.getPublicKey(keyPair);
        privateKeyStr=RSAUtil.getPrivateKey(keyPair);
        Map<String,Object> data = new HashMap<String,Object>();
        Map<String,Object> publicKeyObject = new HashMap<String,Object>();
        publicKeyObject.put("publicKeyStr", publicKeyStr);
        data.put("code",200);
        data.put("data",publicKeyObject);
        return data;
    }
    /*@ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等）
    name–参数名
    value–参数说明
    required–是否必填*/
    @ApiOperation(value="登录", notes="")
    @PostMapping(value = "/login1")
    @ResponseBody
    public ApiResult login1(HttpServletRequest request, @RequestBody @ApiParam(name = "用户对象",value = "传入json",required = true) Users user){
        ApiResult apiResult=new ApiResult();
        try {
            byte[] content= Base64.decodeBase64(user.getPassword());
            PrivateKey privateKey= RSAUtil.string2PrivateKey(privateKeyStr);//公钥解密 从字符串中加载私钥
            byte[] privateDecrypt = RSAUtil.privateDecrypt(content, privateKey);//私钥解密
            System.out.println("解密后的明文: " + new String(privateDecrypt));
            String password=new String(privateDecrypt);
            String username=user.getUsername();
            //查询用户信息
            user.setUsername(username);
            user.setPassword(password);
            Users users=usersService.getUsers(user);
            System.out.println("用户信息 " + users);
            String sessionId=request.getSession().getId();
            System.out.println(sessionId);
//		创建token并返回用户信息，保存token,sessionId,timeStamp
            Map<String,Object> data = new HashMap<String,Object>();
            if(users!=null) {
                long timeStamp=new Date().getTime();
                String accessToken= AccessToken.createToken(users.getUserId(),timeStamp);
                //查询旧的token并清除
                String oldToken=redisUtil.get(users.getUserId());
                if(oldToken!=null&&(oldToken!=accessToken)) {
                    redisUtil.del(oldToken);
                    System.out.println("删掉旧的token");
                }
                Map<String,Object> Authentication =new HashMap<String,Object>();
                Authentication.put("sessionId", sessionId);
                Authentication.put("timeStamp", timeStamp);
                Authentication.put("user_id",users.getUserId());
                redisUtil.hmset(accessToken, Authentication);
                redisUtil.set(users.getUserId(),accessToken);
                /*
                 * System.out.println(isExit); Map<Object,Object>
                 * author=redisUtil.hmget(accessToken);
                 * System.out.println(author.get("sessionId"));
                 */
                data.put("code",200);
                data.put("accessToken", accessToken);
                data.put("message","登录成功");
                Map<String,Object> userData =new HashMap<String,Object>();
                userData.put("userId",users.getUserId());
                userData.put("username",users.getUsername());
                data.put("data",userData);
            }else {
                data.put("code",101);
                data.put("message","用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResult;
    }

    @GetMapping(value = "/getUser")
    public void getUser(@RequestParam @ApiParam(name="username",value="用户名") String username){

    }

    @GetMapping(value = "/test/{userId}")
    public void test1(@PathVariable String userId){

    }
}
