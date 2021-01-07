package com.wangshen.eyeoftwilight.alibaba.nacos.controller;

//import org.springframework.beans.factory.annotation.Value;
//import org.spriAnnotationActionngframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 测试nacos多配置文件
 *
 * @author Administrator
 */
//@RefreshScope
@Controller
@RequestMapping("/nacos")
public class NacosController
{
//	@Value("${com.wangshen}")
	private String myName;

	@RequestMapping("/prifiles")
	public void prifiles()
	{
		System.out.println(myName);
	}
}
