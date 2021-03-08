package com.changgou.order.interceptor;

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
         * 获取用户的令牌
         * 将令牌封装到头文件中
         */


    }
}
