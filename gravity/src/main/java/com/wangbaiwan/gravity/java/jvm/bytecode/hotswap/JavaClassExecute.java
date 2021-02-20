package com.wangbaiwan.gravity.java.jvm.bytecode.hotswap;

import java.lang.reflect.Method;

/**
 * Java class执行工具
 *
 * @author eyeoftwilight
 */
public class JavaClassExecute
{
	public static String execute(byte[] classByte)
	{
		HackSystem.clearBuffer();
		ClassModifier classModifier = new ClassModifier(classByte);
		byte[] modifiedByte = classModifier.modifyUtf8Constant("java/lang/System", "com/wangshen/java/jvm/bytecode/hotswap" + "/HackSystem");
		HotSwapClassLoader hotSwapClassLoader = new HotSwapClassLoader();
		Class class1 = hotSwapClassLoader.loadByte(modifiedByte);
		try
		{
			Method method = class1.getMethod("main", new Class[]{String[].class});
			method.invoke(null, new String[]{null});
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return HackSystem.getBufferString();
	}
}
