package com.example.board.configuration;

import com.example.board.interceptor.LoggerInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfiguration
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoggerInterceptor());
	}    
}