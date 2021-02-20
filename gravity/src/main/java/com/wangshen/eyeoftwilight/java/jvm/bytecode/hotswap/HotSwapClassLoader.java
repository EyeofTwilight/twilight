package com.wangshen.eyeoftwilight.java.jvm.bytecode.hotswap;

/**
 * 实现可以在服务器执行临时代码功能
 *
 * @author
 */
public class HotSwapClassLoader extends ClassLoader
{
	public HotSwapClassLoader()
	{
		// 指定加载HotSwapClassLoader的类加载器为父类加载器
		// 这一步是实现提交的代码可以访问服务端引用库的关键
		super(HotSwapClassLoader.class.getClassLoader());
	}

	public Class loadByte(byte[] classByte)
	{
		return this.defineClass(null, classByte, 0, classByte.length);
	}
}
