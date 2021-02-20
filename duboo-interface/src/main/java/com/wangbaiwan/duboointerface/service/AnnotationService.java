package com.wangbaiwan.duboointerface.service;

import com.wangbaiwan.model.Result;

/**
 * @author wangbaiwan
 * @version 0.1
 * @date 2021-02-21
 */
public interface AnnotationService
{
	/**
	 * 测试dubbo通过zookeeper调用
	 *
	 * @param name 名字
	 * @return 结果字符串
	 */
	Result<String> sayHello(String name);
}
