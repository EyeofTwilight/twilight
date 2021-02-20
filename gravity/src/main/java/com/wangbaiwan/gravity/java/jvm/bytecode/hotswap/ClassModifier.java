package com.wangbaiwan.gravity.java.jvm.bytecode.hotswap;

/**
 * 修改class文件，暂时只修改常量池常量的功能
 *
 * @author wangshen
 */
public class ClassModifier
{
	/**
	 * class文件中常量池的起始偏移位置
	 * 具体可参照class文件格式章节
	 */
	private static final int CONSTANT_POOL_COUNT_INDEX = 8;

	/**
	 * CONSTANT_Utf8_info_tag常量的tag标志
	 */
	private static final int CONSTANT_Utf8_info_tag = 1;

	/**
	 * 常量池中17中常量所占的长度,CONSTANT_Utf8_Info除外，因为它不是定长的
	 */
	private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5, 4, 3, 5, 5, 3, 3};

	private static final int u1 = 1;
	private static final int u2 = 2;

	private byte[] classByte;

	public ClassModifier(byte[] classByte)
	{
		this.classByte = classByte;
	}

	/**
	 * 修改常量池中CONSTSNT_Utf8_info常量的内容
	 *
	 * @param oldStr 修改前的字符串
	 * @param newStr 修改后的
	 * @return
	 */
	public byte[] modifyUtf8Constant(String oldStr, String newStr)
	{
		int constantPoolCount = this.getConstantPoolCount();
		int offset = CONSTANT_POOL_COUNT_INDEX + u2;
		for (int i = 0; i < constantPoolCount; i++)
		{
			int tag = ByteUtils.bytes2Int(classByte, offset, u1);
			if (tag == CONSTANT_Utf8_info_tag)
			{
				int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
				offset += (u1 + u2);
				String str = ByteUtils.byte2String(classByte, offset, len);
				if (str.equalsIgnoreCase(oldStr))
				{
					byte[] newStrBytes = ByteUtils.string2Bytes(newStr);
					byte[] newStrLen = ByteUtils.int2Bytes(newStr.length(), u2);
					classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, newStrLen);
					classByte = ByteUtils.bytesReplace(classByte, offset, len, newStrBytes);
					return classByte;
				}
				else
				{
					offset += len;
				}
			}
			else
			{
				offset += CONSTANT_ITEM_LENGTH[tag];
			}
		}
		return classByte;
	}

	/**
	 * 获取常量池中常量的数量
	 *
	 * @return
	 */
	public int getConstantPoolCount()
	{
		return ByteUtils.bytes2Int(classByte, CONSTANT_POOL_COUNT_INDEX, u2);
	}
}
