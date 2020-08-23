package com.wangshen.dubboconsumer;

import com.wangshen.dubboconsumer.service.impl.AnnotationAction;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DubboConsumerApplicationTests
{
	@Resource
	private AnnotationAction annotationAction;

	@Test
	void contextLoads()
	{
		System.out.println(annotationAction.doSayHello("wangshen"));
	}
}
