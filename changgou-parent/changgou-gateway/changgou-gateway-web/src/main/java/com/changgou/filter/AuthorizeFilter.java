package com.changgou.filter;


import com.changgou.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description: 全局过滤器
 * @author: Kecheng Xie
 * @since: 2020-07-27 14:52
 **/
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";


    /**
     * 全局过滤器
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取Request、Response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();

        //如果是登录、goods等开放的微服务[这里的goods部分开放],则直接放行,这里不做完整演示，完整演示需要设计一套权限系统
        if (path.startsWith("/api/user/login") || path.startsWith("/api/brand/search/")) {
            //放行
            Mono<Void> filter = chain.filter(exchange);
            return filter;
        }

        //从头文件中获取令牌
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        boolean hastoken = true;

        //如果头文件中没有，则从请求参数中获取
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hastoken=false;
        }
        //如果请求参数没有令牌,则从cookie中获取token
        if (StringUtils.isEmpty(token)){
            HttpCookie first = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if (first!=null){
                token=first.getValue();
            }
        }


         //如果没有令牌,则拦截
        if (StringUtils.isEmpty(token)) {
            //设置方法不允许被访问，401错误代码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }else {
            if (!hastoken){
                if (!token.startsWith("bearer ")&&!token.startsWith("Bearer ")){
                    token="bearer "+token;
                }
                request.mutate().header(AUTHORIZE_TOKEN,token);
            }

        }

//        //解析令牌数据
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//            //解析失败，响应401错误
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return response.setComplete();
//        }






        //放行
        return chain.filter(exchange);
    }


    /**
     * 过滤器执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
