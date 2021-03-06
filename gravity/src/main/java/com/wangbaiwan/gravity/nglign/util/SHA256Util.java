package com.wangbaiwan.gravity.nglign.util;

import java.security.MessageDigest;

/**
 * @author Administrator
 */
public class SHA256Util
{

	/**
	 * 利用java原生的类实现SHA256加密
	 *
	 * @param str 加密后的报文
	 * @return
	 */
	public static String getSHA256(String str)
	{
		MessageDigest messageDigest;
		String encodeStr = "";
		try
		{
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return encodeStr;
	}


	/**
	 * 将byte转为16进制
	 *
	 * @param bytes
	 * @return
	 */
	private static String byte2Hex(byte[] bytes)
	{
		StringBuffer stringBuffer = new StringBuffer();
		String temp;
		for (int i = 0; i < bytes.length; i++)
		{
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1)
			{
				//1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}
}
