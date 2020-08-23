package com.wangshen.eyeoftwilight.alibaba.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wangshen.duboointerface.service.AnnotationService;

/**
 * @author Administrator
 */
@Service
public class AnnotationServiceImpl implements AnnotationService
{
	/**
	 * dubbo测试使用
	 *
	 * @param name
	 * @return
	 */
	@Override
	public String sayHello(String name)
	{
		return "annotation: hello, " + name;
	}
}
