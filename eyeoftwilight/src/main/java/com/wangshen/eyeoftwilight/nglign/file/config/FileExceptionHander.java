package com.wangshen.eyeoftwilight.nglign.file.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

/**
 * 附件相关异常处理
 *
 * @author 往事，优雅而已
 * @date 2019-07-28
 */
@RestControllerAdvice
public class FileExceptionHander
{
	/**
	 * 上传附件，附件大小超过限制时一场处理
	 * spring默认上传大小1MB 超出大小捕获异常MaxUploadSizeExceededException
	 * 这里我自己配置大小限制为：10Mb
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Map<String, Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e)
	{
		Map<String, Object> map = new HashMap<>(8);
		map.put("code", 500);
		map.put("msg", "文件大小超出10Mb限制, 请压缩或降低文件质量 !");
		return map;
	}
}
