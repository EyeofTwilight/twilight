package com.wangshen.eyeoftwilight.baidu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author 往事，优雅而已
 */
public class OcrUtil
{
	/**
	 * 把文件读取成byte[]
	 *
	 * @param filePath
	 * @return
	 */
	public static byte[] readFile(String filePath)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		File file = new File(filePath);
		byte[] buffer = new byte[1024 * 4];
		try
		{
			if (file.exists())
			{
				FileInputStream fileInputStream = new FileInputStream(file);
				int n = 0;
				while ((n = fileInputStream.read(buffer)) != -1)
				{
					out.write(buffer, 0, n);
				}
				return out.toByteArray();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new byte[0];
	}
}
