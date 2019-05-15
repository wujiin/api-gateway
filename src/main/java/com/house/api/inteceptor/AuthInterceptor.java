package com.house.api.inteceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.api.common.RestResponse;
import com.house.api.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.google.common.base.Joiner;
import com.house.api.common.CommonConstants;
import com.house.api.common.UserContext;

/**
 * @Description 自定义拦截器  鉴权处理
 * @Author wujin
 **/
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String TOKEN_COOKIE = "token";

    @Value("${user.service.name}")
    private String userServiceName;

    private static final String directFlag = "direct://";

    /**
     * @Description 拦截请求鉴权处理
     **/
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        //获取参数
        Map<String, String[]> map = req.getParameterMap();
        map.forEach((k, v) -> req.setAttribute(k, Joiner.on(",").join(v)));
        String requestURI = req.getRequestURI();
        //判断是否为静态资源或错误页面转发
        if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
            return true;
        }
        //获取cookie中的token
        Cookie cookie = WebUtils.getCookie(req, TOKEN_COOKIE);
        if (cookie != null && StringUtils.isNoneBlank(cookie.getValue())) {
            //获取用户信息  这里使用feign调用初始化的时候出现  服务调用死循环！ 改用restTemplete调用
            User user = getUserByToken(cookie.getValue());
            //返回用户信息
            if (user != null) {
                req.setAttribute(CommonConstants.LOGIN_USER_ATTRIBUTE, user);
                UserContext.setUser(user);
            }
        }
        return true;
    }

    /**
     * @Description 调用鉴权服务
     **/
    @HystrixCommand(fallbackMethod = "getUserByTokenFb")
    public User getUserByToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + userServiceName + "/user/get?token=" + token;
        url = url.replace(directFlag, "");
        ResponseEntity<RestResponse<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<RestResponse<User>>() {
        });
        RestResponse<User> response = responseEntity.getBody();
        if (response == null || response.getCode() != 0) {
            return null;
        }
        return response.getResult();
    }


    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String requestURI = req.getRequestURI();
        if (requestURI.startsWith("/static") || requestURI.startsWith("/error")) {
            return;
        }
        User user = UserContext.getUser();
        if (user != null && StringUtils.isNoneBlank(user.getToken())) {
            String token = requestURI.startsWith("logout") ? "" : user.getToken();
            Cookie cookie = new Cookie(TOKEN_COOKIE, token);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            res.addCookie(cookie);
        }

    }


    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        UserContext.remove();
    }
}
