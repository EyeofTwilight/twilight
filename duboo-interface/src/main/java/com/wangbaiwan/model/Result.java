package com.wangbaiwan.model;

/**
 * @author wangbaiwan
 */
public class Result<T>
{
	/**
	 * 返回数据
	 */
	private T data;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 错误码
	 */
	private String errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;

	public Result(T data)
	{
		this.data = data;
		this.success = true;
		this.errCode = "0";
		this.errMsg = "Success";
	}

	public boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	public String getErrCode()
	{
		return errCode;
	}

	public void setErrCode(String errCode)
	{
		this.errCode = errCode;
	}

	public String getErrMsg()
	{
		return errMsg;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}

	@Override
	public String toString()
	{
		return "Result{" +
				"data=" + data +
				", success=" + success +
				", errCode='" + errCode + '\'' +
				", errMsg='" + errMsg + '\'' +
				'}';
	}
}
