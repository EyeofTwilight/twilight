package com.wangbaiwan.gravity.nglign.i18n.controller;

import com.wangbaiwan.gravity.nglign.i18n.service.I18nService;
import com.wangbaiwan.gravity.nglign.common.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/i18n")
public class I18nController
{
	@Autowired
	private I18nService i18nService;

	/**
	 * 获取国际化语言
	 *
	 * @return
	 */
	@RequestMapping("/getLanguageData/{type}")
	public R getLanguageData(@PathVariable String type)
	{
		return i18nService.getLanguageData(type);
	}
}
