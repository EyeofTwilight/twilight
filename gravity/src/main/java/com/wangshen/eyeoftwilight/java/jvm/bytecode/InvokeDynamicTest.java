package com.wangshen.eyeoftwilight.java.jvm.bytecode;

import java.lang.invoke.*;

/**
 * @author eyeoftwilight
 * @date 2020年5月8日 21点13分
 */
public class InvokeDynamicTest
{
	public static void testMethod(String s)
	{
		System.out.println("Hello String :" + s);
	}

	public static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType methodType) throws NoSuchMethodException, IllegalAccessException
	{
		return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, methodType));
	}

	private static MethodType MT_BootstrapMethod()
	{
		return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandle$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
	}

	private static MethodHandle MH_BootstrapMethod() throws NoSuchMethodException, IllegalAccessException
	{
		return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "BootstrapMethod", MT_BootstrapMethod());
	}

	private static MethodHandle INDY_BootstrapMethod() throws Throwable
	{
		CallSite callSite = (CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod", MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
		return callSite.dynamicInvoker();
	}
}
