package com.lanxin.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.lanxin.anno.PassToken;
import com.lanxin.anno.UserLoginToken;
import com.lanxin.bean.Users;
import com.lanxin.util.AccessToken;
import com.lanxin.util.JwtUtil;
import com.lanxin.util.RedisUtil;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;

@Configuration
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        response.setCharacterEncoding("utf-8");
        // JWT验证 如果不是映射到方法直接通过
        /*if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        String token=request.getHeader("accessToken");
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                //验证token是否正确
                boolean result= JwtUtil.verify(token);
                if(result){
                    return true;
                }
            }
        }
        return true;*/
        //执行token校验
        String access_token=request.getHeader("access_token");
        String session_id=request.getSession().getId();
        long timeStamp=new Date().getTime();
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        RedisUtil redisUtil = (RedisUtil) factory.getBean("redisUtil");
        boolean isValid= AccessToken.verfifyToken(access_token,session_id,timeStamp,redisUtil);
        if(isValid) {
            return true;
        }else {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write("{\"code\":401,\"message\":\"token失效，请重新登录\"}");
            pw.flush();
            pw.close();
            return false;
        }
    }
}
