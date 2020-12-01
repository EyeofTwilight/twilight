package com.wangshen.eyeoftwilight;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取controller中url的全路径
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GetRequestUrlTest
{
	@Autowired
	private WebApplicationContext applicationContext;

	@Test
	public void getRequestUrl()
	{
		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
		// 拿到Handler适配器中的全部方法
		Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
		List<String> urlList = new ArrayList<>();
		for (RequestMappingInfo info : methodMap.keySet())
		{
			Set<String> urlSet = info.getPatternsCondition().getPatterns();
			// 获取全部请求方式
			//			Set<RequestMethod> Methods = info.getMethodsCondition().getMethods();
			//			System.out.println(Methods.toString());
			for (String url : urlSet)
			{
				// 加上自己的域名和端口号，就可以直接调用
				//				urlList.add("http://localhost:XXXX" + url);
				System.out.println(url);
			}
		}
		//		System.out.println(urlList);
	}
}
