package com.wangbaiwan.gravity.baidu.service.impl;

import com.baidu.aip.ocr.AipOcr;
import com.wangbaiwan.gravity.baidu.service.OcrService;
import com.wangbaiwan.gravity.baidu.util.OcrUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 百度Ocr
 *
 * @author 往事，优雅而已
 */
@Service
public class OcrServiceImpl implements OcrService
{
	private Logger logger = LoggerFactory.getLogger(OcrServiceImpl.class);

	@Value("${baidu.aip.ocr.appId}")
	public String APP_ID;

	@Value("${baidu.aip.ocr.apiKey}")
	public String API_KEY;

	@Value("${baidu.aip.ocr.secretKey}")
	public String SECRET_KEY;


	/**
	 * 根据身份证路径识别
	 *
	 * @param imgPath
	 */
	@Override
	public void getIdCardInfo(String imgPath)
	{
		byte[] file = OcrUtil.readFile(imgPath);
		this.getIdCardInfo(file);
	}


	/**
	 * 参数为二进制数组
	 *
	 * @param bytes
	 */
	@Override
	public void getIdCardInfo(byte[] bytes)
	{
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>(8);
		// 1.是否检测图像朝向
		options.put("detect_direction", "false");
		// 2.是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能
		options.put("detect_risk", "false");

		String idCardSide = "front";

		JSONObject res = client.idcard(bytes, idCardSide, options);
		logger.info("身份认证返回结果：" + res.toString());
		if (res.getLong("log_id") == 0)
		{
			logger.info("认证失败");
		}
		else
		{
			logger.info("认证成功");
			System.out.println(res.toString(2));
		}

	}
}
