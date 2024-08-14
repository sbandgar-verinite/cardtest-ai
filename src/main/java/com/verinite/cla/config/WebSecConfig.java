package com.verinite.cla.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		registry.addResourceHandler("/META-INF/resources/**").addResourceLocations("classpath:/META-INF/resources/");
	}

}
