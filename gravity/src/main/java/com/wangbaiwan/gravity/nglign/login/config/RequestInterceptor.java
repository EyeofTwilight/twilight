package com.wangbaiwan.gravity.nglign.login.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter
{
	/**
	 * 请求中token值的前缀,相当于一个简单验证，可加可不加
	 */
	@Value("${jwt.token.token-head}")
	private String tokenHead;

	/**
	 * 请求中token的名称
	 */
	String tokenHeader = "Authorization";

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String authHeader = request.getHeader(tokenHeader);
		if (authHeader == null || !authHeader.startsWith(tokenHead))
		{
			return false;
		}
		// The part after "Bearer"
		final String authToken = authHeader.substring(tokenHead.length());
		if (authToken == null || "".equals(authToken.trim()))
		{
			return false;
		}
		// 获取token状态
		int tokenStatus = jwtTokenUtils.tokenStatus(authToken);
		// 1.未过期
		if (tokenStatus == JwtTokenUtils.TOKEN_AVAILABLE)
		{
			// 根据token,通过自定义方式获取 userName
			String userId = jwtTokenUtils.getUserIdFromToken(authToken);
			return true;
		}
		// 2.可刷新
		if (tokenStatus == JwtTokenUtils.TOKEN_CAN_REFRESH)
		{
			response.setStatus(401);
//			response.addHeader("refreshToken", jwtTokenUtils.refreshToken(authToken));
			return false;
		}
		// 3.无效
		if (tokenStatus == JwtTokenUtils.TOKEN_INVALID)
		{
			response.setStatus(403);
			return false;
		}
		return false;
	}
}
