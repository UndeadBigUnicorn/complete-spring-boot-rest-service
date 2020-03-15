package com.xquestions.fullrestcomplete.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        String x = request.getMethod();
        // so now it's possible to do something with user
        try {
            String username = request.getUserPrincipal().getName();
            // for example
//            request.setAttribute("subscription", "");
            logger.info(x + " intercepted for the user - " + username);
        } catch (NullPointerException e) {
            logger.info(x + " intercepted for the anonymous user");
        }

        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
//    {
//        System.out.println("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception
//    {
//        System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
//    }

}
