package com.wangbaiwan.gravity.nglign.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{
	/**
	 * token过滤器
	 */
	@Autowired
	private RequestInterceptor requestInterceptor;

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		List<String> list = new ArrayList<>();
		list.add("/login/account");
		list.add("/login/account?_allow_anonymous=true");
		list.add("/login/refreshToken");
		list.add("/login/logout");
		list.add("/i18n/getLanguageData/*");
		//拦截路径可自行配置多个 可用 ，分隔开
		registry.addInterceptor(requestInterceptor).
				excludePathPatterns(list);
	}

	/**
	 * 跨域支持
	 *
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
				.maxAge(3600 * 24);
	}
}
