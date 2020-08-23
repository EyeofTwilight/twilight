package com.wangshen.eyeoftwilight.nglign.login.service;


import com.wangshen.eyeoftwilight.nglign.common.vo.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录
 *
 * @author Administrator
 */
public interface LoginService
{
	/**
	 * 登陆与授权.
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	R generateToken(Map<String, Object> params, HttpServletRequest request);

	/**
	 * 刷新token
	 *
	 * @param request
	 * @return
	 */
	R refreshToken(HttpServletRequest request);
}
