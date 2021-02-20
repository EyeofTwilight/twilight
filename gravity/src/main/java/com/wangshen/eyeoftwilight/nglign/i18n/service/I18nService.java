package com.wangshen.eyeoftwilight.nglign.i18n.service;

import com.wangshen.eyeoftwilight.nglign.common.vo.R;

/**
 * @author Administrator
 */
public interface I18nService
{
	/**
	 * 获取国际化语言数据
	 *
	 * @param type
	 * @return
	 */
	R getLanguageData(String type);
}
