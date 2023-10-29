/**
 * @BelongsProject: yygh_parent
 * @BelongsPackage: com.zhang.yygh.gateway.filter
 * @Author: 张栩垄
 * @CreateTime: 2023-10-08  10:22
 * @Description: 描述
 * @Version: 1.0
 */

package com.zhang.yygh.gateway.filter;

import com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 过滤器
 */
// @Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    //执行过滤功能
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //对于登录接口的请求进行放行处理
        if (antPathMatcher.match("/admin/user/**",path)){
           return chain.filter(exchange);
        }else {//对于非登录接口。进行验证，必须登录i之后才能通过
            List<String> strings = request.getHeaders().get("X-Token");
            if (strings.size() == 0){

                //拦截
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.FORBIDDEN);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("success", false);
                jsonObject.addProperty("code", 28004);
                jsonObject.addProperty("data", "鉴权失败");
                byte[] bytes = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                //指定编码，否则在浏览器中会中文乱码
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                response.writeWith(Mono.just(buffer));
                return response.setComplete();//结束请求
            }else {
                return chain.filter(exchange);
            }

        }

    }

    //影响的是全局过滤器的执行顺序
    @Override
    public int getOrder() {
        return 0;
    }
}
