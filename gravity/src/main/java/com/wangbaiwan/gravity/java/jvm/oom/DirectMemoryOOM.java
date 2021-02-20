package com.wangbaiwan.gravity.java.jvm.oom;


/**
 * 直接内存（Direct Memory）的容量大小可通过-XX：MaxDirectMemorySize参数来指定，
 * 如果不去指定，则默认与Java堆最大值（由-Xmx指定）一致.
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * @author Administrator
 */
public class DirectMemoryOOM
{
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) throws IllegalAccessException
	{

	}
}
