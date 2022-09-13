package com.app.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	public WebMvcConfig() {

		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ApiLogger apiLogger;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(apiLogger).addPathPatterns("/api/**");

	}

}
