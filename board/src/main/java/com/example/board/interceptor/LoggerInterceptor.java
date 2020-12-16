package com.example.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * LoggerInterceptor
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        log.debug("==================================================START==================================================");
        log.debug(" Request URI \t: " + httpServletRequest.getRequestURI());
        return super.preHandle(httpServletRequest, httpServletResponse, handler);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("==================================================END==================================================");
    }
}