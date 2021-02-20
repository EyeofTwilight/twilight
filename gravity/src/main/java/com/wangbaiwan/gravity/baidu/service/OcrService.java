package com.wangbaiwan.gravity.baidu.service;

/**
 * @author 往事，优雅而已
 */
public interface OcrService
{
	/**
	 * 根据身份证路径识别
	 *
	 * @param imgPath
	 */
	void getIdCardInfo(String imgPath);


	/**
	 * 参数为二进制数组
	 *
	 * @param bytes
	 */
	void getIdCardInfo(byte[] bytes);
}
