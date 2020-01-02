package com.test.mygateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.mygateway.exception.CommonEnum;
import com.test.mygateway.exception.CustomException;
import com.test.mygateway.utils.JwtUtils;
import com.test.mygateway.utils.UrlResolver;
import com.test.mygateway.utils.UrlWhileList;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * JwtToken过滤器
 *
 * @author MicalJ
 * @create 2019/12/5 11:51
 **/
@Component
@Setter
@Getter
@Slf4j
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private String[] skipAuthUrls = new String[]{"/user/login", "/user/to_login", "/static/",
            "/static/css/style.css", "/static/js/jquery.min.js", "/static/js/vue.js"};

    public static final String login_url = "/user/login";

    private ObjectMapper objectMapper;

    public JwtTokenFilter(ObjectMapper mapper) {
        this.objectMapper = mapper;
    }

    /**
     * 过滤器
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String url = exchange.getRequest().getURI().getPath();
        //跳过不需要验证的路径
        if (UrlResolver.check(UrlWhileList.getUrlList(), url)) {
            return chain.filter(exchange);
        }

        //获取token
        List<String> authorization = exchange.getRequest().getHeaders().get("authorization");
        if (authorization == null || authorization.size() == 0) {
            return goToLogin(exchange, login_url);
        }
        String token = authorization.get(0);
        if (StringUtils.isBlank(token)) {
            return goToLogin(exchange, login_url);
            //没有token
        } else {
            //有token
            try {
                //是否能正常解析token
                Claims claims = JwtUtils.parseJWT(token);
                //判断redis中是否存在token
                //TODO
                //判断是否需要为用户重新生成token
                boolean expiration = JwtUtils.isExpiration(claims);
                if (expiration){
                    //没过期就直接放过，执行后面的流程
                    return chain.filter(exchange);
                }
                //过期了，就重新生成token
                String newToken = JwtUtils.createJWT(claims.get("userId", Integer.class), claims.getSubject());

            } catch (ExpiredJwtException eje) {
                log.error("===== Token过期 =====" + eje.getMessage());
            } catch (Exception e) {
                log.error("===== token解析异常 =====" + e.getMessage());
            }
        }
        return goToLogin(exchange, login_url);
    }



    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> goToLogin(ServerWebExchange exchange, String url) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set(HttpHeaders.LOCATION, url);
        Set<String> strings = response.getHeaders().keySet();
        for (String string : strings) {
            System.out.println(string + ": " + response.getHeaders().get(string));
        }
        Mono<Void> voidMono = response.setComplete();
        return voidMono;
    }

//    private Mono<Void> setAuthorization(ServerWebExchange exchange, String token){
//        ServerHttpRequest request = exchange.getRequest();
//        request.getHeaders().set("authorization", token);
//        ServerHttpResponse response = exchange.getResponse();
//        response.getHeaders().set("authorization", token);
//        return response.setComplete();
//    }
}
