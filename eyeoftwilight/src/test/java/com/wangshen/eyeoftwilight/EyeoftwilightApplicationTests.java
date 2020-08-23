package com.wangshen.eyeoftwilight;

import com.wangshen.eyeoftwilight.nglign.login.domain.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@SpringBootTest
class EyeoftwilightApplicationTests
{

	@Test
	void contextLoads()
	{

	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException
	{
		Class class1 = Class.forName("java.lang.String");
		Class class2 = "123".getClass();
		Class class3 = String.class;

		Method[] methods = class1.getMethods();
		Method method = class1.getDeclaredMethod("toString");
		// 获取私有方法
		method.setAccessible(true);
		// 返回一个描述此方法的字符串，包括类型参数
		System.out.println(method.toGenericString());

		Field[] fields = class1.getDeclaredFields();
		Field field = class1.getDeclaredField("value");
		// 获取私有的字段
		field.setAccessible(true);
		byte[] value = {'1', '9', '8'};
		String aa = "123";
		// set会把aa的值变成198
		field.set(aa, value);
	}

}
