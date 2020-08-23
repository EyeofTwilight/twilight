package com.wangshen.eyeoftwilight.nglign.login.vo;

import lombok.Data;

/**
 * 用于登录时返回token及用户信息给页面
 *
 * @author eyeoftwilight
 */
@Data
public class TokenVO
{
	private String authToken;

	/**
	 * 错误码，0:正确 非0错误
	 */
	private Integer errCode;

	/**
	 * 错误信息
	 */
	private String errMsg;

	/**
	 * 用户信息
	 */
	private SysUserVO user;
}
