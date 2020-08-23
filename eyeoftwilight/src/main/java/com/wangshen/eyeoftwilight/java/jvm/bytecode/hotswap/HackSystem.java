package com.wangshen.eyeoftwilight.java.jvm.bytecode.hotswap;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author Administrator
 */
public class HackSystem
{
	public final static InputStream in = System.in;

	private static ByteArrayOutputStream buffer = new ByteArrayOutputStream();

	private final static PrintStream out = new PrintStream(buffer);

	public final static PrintStream err = out;

	public static String getBufferString()
	{
		return buffer.toString();
	}

	public static void clearBuffer()
	{
		buffer.reset();
	}

	/*************** 一下方法都是java.lang.System的名称一样，实现都是转调System的对应方法  ***************/

	public static void setSecurityManager(final SecurityManager s)
	{
		System.setSecurityManager(s);
	}

	public static SecurityManager getSecurityManager()
	{
		return System.getSecurityManager();
	}

	public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
	{
		System.arraycopy(src, srcPos, dest, destPos, length);
	}

	public static int identityHashCode(Object x)
	{
		return System.identityHashCode(x);
	}

}
