package com.wangbaiwan.dubboconsumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wangbaiwan.duboointerface.service.AnnotationService;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class AnnotationAction
{
	@Reference
	private AnnotationService annotationService;

	/**
	 * 测试dubbo通过zookeeper调用
	 *
	 * @param name
	 * @return
	 */
	public String doSayHello(String name)
	{
		return annotationService.sayHello(name);
	}
}
