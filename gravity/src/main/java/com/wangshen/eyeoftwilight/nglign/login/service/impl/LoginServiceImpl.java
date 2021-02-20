package com.wangshen.eyeoftwilight.nglign.login.service.impl;

import com.wangshen.eyeoftwilight.nglign.login.config.JwtTokenUtils;
import com.wangshen.eyeoftwilight.nglign.login.domain.SysUserDO;
import com.wangshen.eyeoftwilight.nglign.login.service.LoginService;
import com.wangshen.eyeoftwilight.nglign.login.service.SysUserService;
import com.wangshen.eyeoftwilight.nglign.common.vo.R;
import com.wangshen.eyeoftwilight.nglign.login.vo.SysUserVO;
import com.wangshen.eyeoftwilight.nglign.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService
{
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 请求中token值的前缀,相当于一个简单验证，可加可不加
	 */
	@Value("${jwt.token.token-head}")
	private String tokenHead;


	/**
	 * 登陆与授权.
	 *
	 * @param params
	 * @param request
	 * @return
	 */
	@Override
	public R generateToken(Map<String, Object> params, HttpServletRequest request)
	{
		String userName = params.get("userName") == null ? null : params.get("userName").toString();
		String password = params.get("password") == null ? null : params.get("password").toString();
		R msg = new R();

		// 1.从数据库获取用户信息
		SysUserVO sysUserVO = sysUserService.getUserInfoByUserId(userName);
		if (sysUserVO == null)
		{
			msg.setErrCode("1");
			msg.setErrMsg("用户不存在!");
			return msg;
		}

		// 2.验证密码和加密后的密码是否一致
		if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(sysUserVO.getPassword()))
		{
			Map<String, Object> returnInfo = this.generateToken1(sysUserVO, request);
			msg.setErrCode("0");
			msg.setErrMsg("获取token成功");
			msg.setReturnInfo(returnInfo);
			return msg;
		}
		msg.setErrCode("1");
		msg.setErrMsg("密码错误");
		return msg;
	}

	/**
	 * 刷新token
	 *
	 * @param request
	 * @return
	 */
	@Override
	public R refreshToken(HttpServletRequest request)
	{
		String refreshToken = request.getHeader("refresh_token");
		if (refreshToken == null || refreshToken.trim().equals(""))
		{
			return R.ok("");
		}
		Map<String, Object> result = new HashMap<>(16);
		int tokenStatus = jwtTokenUtils.tokenStatus(refreshToken);
		// 1.未过期
		if (tokenStatus == JwtTokenUtils.TOKEN_AVAILABLE)
		{
			String userId = jwtTokenUtils.getUserIdFromToken(refreshToken);
			SysUserDO sysUserDO = sysUserService.get(Long.valueOf(userId));
			result = this.generateToken1(sysUserDO, request);
		}
		else
		{

		}
		return R.ok(result);
	}

	/**
	 * 获取token
	 *
	 * @param sysUser
	 * @return
	 */
	public Map<String, Object> generateToken1(SysUserDO sysUser, HttpServletRequest request)
	{
		Map<String, Object> claims = new HashMap<>(16);
		claims.put("userId", sysUser.getId());
		claims.put("userName", sysUser.getName());
		claims.put("email", sysUser.getEmail());
		// 生产token,并持久化到redis
		String token = jwtTokenUtils.generateToken(claims);
		log.info("token: " + token);
		String token2Exp = jwtTokenUtils.generateToken2Exp(claims);
		log.info("2倍过期时间token: " + token2Exp);
		boolean isMobile = CommonUtils.isMobile(request);
		String prefix = "pc-";
		if (isMobile)
		{
			prefix = "mobile-";
		}
		stringRedisTemplate.opsForValue().set(prefix + sysUser.getName(), token);
		Map<String, Object> resultInfo = new HashMap<>(16);
		resultInfo.put("token", token);
		resultInfo.put("user", sysUser);
		resultInfo.put("refresh_token", token2Exp);
		return resultInfo;
	}
}
