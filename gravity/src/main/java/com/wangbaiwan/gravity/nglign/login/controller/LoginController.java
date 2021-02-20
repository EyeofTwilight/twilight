package com.wangbaiwan.gravity.nglign.login.controller;


import com.wangbaiwan.gravity.nglign.login.service.LoginService;
import com.wangbaiwan.gravity.nglign.common.vo.R;
import com.wangbaiwan.gravity.nglign.login.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/login")
public class LoginController
{
	@Autowired
	private LoginService loginService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	/**
	 * 登录获取token
	 *
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/account")
	public R login(@RequestBody Map<String, Object> params, HttpServletRequest request)
	{
		return loginService.generateToken(params, request);
	}

	/**
	 * 刷新token
	 *
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/refreshToken")
	public R refreshToken(HttpServletRequest request)
	{
		return loginService.refreshToken(request);
	}

	/**
	 * 登录获取token
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.DELETE)
	public TokenVO logout()
	{
		String userName = stringRedisTemplate.opsForValue().get("wangshen");
		TokenVO token = new TokenVO();
		token.setErrCode(0);
		return token;
	}
}
