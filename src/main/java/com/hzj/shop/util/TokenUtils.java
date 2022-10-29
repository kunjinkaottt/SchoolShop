package com.hzj.shop.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class TokenUtils {

//    token生成器
    public static String genToken(String id,String sign){
        return JWT.create().withAudience(id)//将用户id保存到token里面作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),1))//token在1小时后失效
                .sign(Algorithm.HMAC256(sign));//以password作为token的密钥
    }

}
