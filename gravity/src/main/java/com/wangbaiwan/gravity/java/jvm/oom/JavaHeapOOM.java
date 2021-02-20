package com.wangbaiwan.gravity.java.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * new对象时，在堆上申请不到足够的内存，即溢出
 * VM Args: -Xms10M -Xmx10M -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author wangshen
 */
public class JavaHeapOOM
{
	static class OOMObject
	{

	}

	public static void main(String[] args)
	{
		List<OOMObject> list = new ArrayList<>();
		while (true)
		{
			list.add(new OOMObject());
		}
	}
}
