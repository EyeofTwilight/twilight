package com.wangshen.eyeoftwilight.baidu.controller;

import com.wangshen.eyeoftwilight.baidu.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 往事，优雅而已
 */
@Controller
@RequestMapping("/ocr")
public class OcrController
{
	@Autowired
	private OcrService ocrService;


	/**
	 * 获取身份证正面信息
	 */
	@RequestMapping("/getIdCardInfo")
	public void getIdCardInfo()
	{
		String imgPath = "C:\\Users\\往事，优雅而已\\Desktop\\微信图片_20190710211947.jpg";
		ocrService.getIdCardInfo(imgPath);
	}
}
