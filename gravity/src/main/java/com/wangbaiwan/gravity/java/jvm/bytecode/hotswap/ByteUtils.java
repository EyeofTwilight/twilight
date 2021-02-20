package com.wangbaiwan.gravity.java.jvm.bytecode.hotswap;

/**
 * byte数组处理工具
 * 其中2英文two和to同音，转的意思
 *
 * @author eyeoftwilight
 */
public class ByteUtils
{
	/**
	 * 从字节数组指定位置start开始，取len个字节，并转成int
	 *
	 * @param bytes
	 * @param start
	 * @param len   要取的字节长度
	 * @return
	 */
	public static int bytes2Int(byte[] bytes, int start, int len)
	{
		int end = start + len;
		int sum = 0;
		int move = 4;
		for (int i = start; i < end; i++)
		{
			// 分两种情况(备注：& 按位与,都是1才是1,否则0)
			// 1.正数 127
			// 127 -> 01111111 byte 8位二进制 -> 00000000 00000000 00000000 011111111 int 32位二进制
			// 00000000 00000000 00000000 011111111 & 0xff = 00000000 00000000 00000000 01111111 结果不变
			// 2.负数 -127
			// -127 -> 10000001 byte 8位二进制 -> 11111111 11111111 11111111 10000001 int 32位二进制
			// 11111111 11111111 11111111 10000001 & 0xff = 00000000 00000000 00000000 10000001 结果不变
			// 即用于保持二进制补码的一致性,但其对应的10进制，已经发生变化了
			int n = ((int) bytes[i]) & 0xff;
			// 保持二进制补码的一致性，便是用来在此处，对于多个字节的数字，高位需要左移
			// 如： 10101010 01010101，每次取一个byte 8位，所以对于高位要保持二进制补码的一致性
			// 以免左移带来影响,即若是两个字节转int，则第一个字节需左移24位，第二个字节需左移12位，以保持最后结果正确
			// 问题？ 若是4个以上的字节转int，若已经超过int最大值，则此方法是有问题的
			// n << (--len) * 8 到底行不行？
			n <<= (--move) * 8;
			sum += n;
		}
		return sum;
	}

	public static String byte2String(byte[] classByte, int start, int len)
	{
		return new String(classByte, start, len);
	}

	public static byte[] string2Bytes(String str)
	{
		return str.getBytes();
	}

	/**
	 * 把int转成指定长度的byte数组
	 *
	 * @param value
	 * @param len
	 * @return
	 */
	public static byte[] int2Bytes(int value, int len)
	{
		byte[] bytes = new byte[len];
		for (int i = 0; i < len; i++)
		{
			// 11111111 11111111 11111111 01111111‬
			bytes[i] = (byte) ((value >> 8 * (len - i - 1)) & 0xff);
		}
		return bytes;
	}

	/**
	 * 把originalBytes中偏移量为offset长度为len的字节，替换成replaceBytes
	 *
	 * @param originalBytes
	 * @param offset
	 * @param len
	 * @param replaceBytes
	 * @return
	 */
	public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes)
	{
		byte[] newBytes = new byte[originalBytes.length + replaceBytes.length - len];
		System.arraycopy(originalBytes, 0, newBytes, 0, offset);
		System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
		System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length, originalBytes.length - offset - len);
		return newBytes;
	}
}
