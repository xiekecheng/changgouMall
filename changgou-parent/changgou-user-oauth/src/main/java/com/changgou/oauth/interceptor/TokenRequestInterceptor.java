package com.changgou.oauth.interceptor;

import com.changgou.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiekecheng
 */
@Configuration
public class TokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        /**
         * 从数据库加载查询用户信息
         * 1:没有令牌,Feign调用之前,生成令牌
         * 2:Feign调用之前,令牌需要携带过去
         * 3:Feign调用之前,令牌需要存放到Header文件中
         * 4:请求->Feign调用->拦截器RequestInterceptor->Feign调用之前执行拦截
         */
        String token = AdminToken.adminToken();
        requestTemplate.header("Authorization","bearer "+token);

    }
}
