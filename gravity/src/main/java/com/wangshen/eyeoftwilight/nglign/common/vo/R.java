package com.wangshen.eyeoftwilight.nglign.common.vo;

import lombok.Data;

/**
 * @author rongwei
 */
@Data
public class R
{
	/**
	 * 状态码 0:成功，非0失败
	 */
	private String errCode;

	/**
	 * 错误信息
	 */
	private String errMsg;

	/**
	 * 返回给页面的数据
	 */
	private Object returnInfo;

	public static R ok(Object o)
	{
		R msg = new R();
		msg.setErrCode("0");
		msg.setErrMsg("ok");
		msg.setReturnInfo(o);
		return msg;
	}
}
