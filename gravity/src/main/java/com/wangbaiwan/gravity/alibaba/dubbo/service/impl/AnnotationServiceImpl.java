package com.wangbaiwan.gravity.alibaba.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wangbaiwan.duboointerface.service.AnnotationService;
import com.wangbaiwan.gravity.nglign.login.vo.SysUserVO;
import com.wangbaiwan.model.Result;

/**
 * 测试使用Dubbo的@Service注解向Zookeeper中注入
 *
 * @author wangbaiwan
 */
@Service
public class AnnotationServiceImpl implements AnnotationService
{
	/**
	 * 测试dubbo通过zookeeper调用
	 *
	 * @param name 名字
	 * @return 结果字符串
	 */
	@Override
	public Result<String> sayHello(String name)
	{
		return new Result<>("annotation: hello, " + name);
	}

	/**
	 * 测试返回对象问题
	 *
	 * @param name 名字
	 * @return 结果字符串
	 */
	public SysUserVO sayHello2(String name)
	{
		return new SysUserVO();
	}
}
