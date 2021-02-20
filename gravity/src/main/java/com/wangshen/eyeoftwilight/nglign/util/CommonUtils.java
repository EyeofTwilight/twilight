package com.wangshen.eyeoftwilight.nglign.util;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shangmi
 * @date 2020年4月23日
 */
public class CommonUtils
{
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	private static final Pattern idNumber15 = Pattern.compile("^(\\d{6})(\\d{2})(\\d{2})(\\d{2})(\\d{3})$");
	private static final Pattern idNumber18 = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");

	/**
	 * 输入身份证号计算年龄
	 *
	 * @param idNumber
	 * @return
	 */
	public static Integer calculateAge(String idNumber)
	{
		Matcher arrSplit;
		Date dtmBirth = null;
		String group2 = null, group3 = null, group4 = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		if (idNumber == null || idNumber.trim() == "")
		{
			return null;
		}
		idNumber = idNumber.replaceAll("s*g", "");
		if (idNumber.length() == 15 && idNumber15.matcher(idNumber.trim()).matches())
		{
			arrSplit = idNumber15.matcher(idNumber);
			while (arrSplit.find())
			{
				group2 = arrSplit.group(2);
				group3 = arrSplit.group(3);
				group4 = arrSplit.group(4);
			}
			dtmBirth = new Date(group2 + '/' + group3 + '/' + group4);
		}
		else if (idNumber.length() == 18 && idNumber18.matcher(idNumber.trim()).matches())
		{
			arrSplit = idNumber18.matcher(idNumber);
			while (arrSplit.find())
			{
				group2 = arrSplit.group(2);
				group3 = arrSplit.group(3);
				group4 = arrSplit.group(4);
			}
			try
			{
				dtmBirth = simpleDateFormat.parse(group2 + '/' + group3 + '/' + group4);
				//				dtmBirth = new Date(group2 + '/' + group3 + '/' + group4);
			}
			catch (ParseException e)
			{
				logger.info("日期转换异常：" + e.getMessage());
			}
		}
		else
		{
			return null;
		}

		if (dtmBirth == null)
		{
			return null;
		}
		long diff = System.currentTimeMillis() - dtmBirth.getTime();
		Double age = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);
		Integer result = age == null ? null : age.intValue();
		return result;
	}

	/**
	 * 判断请求是否手机端
	 *
	 * @param req
	 * @return
	 */
	public static boolean isMobile(HttpServletRequest req)
	{
		UserAgent ua = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));
		OperatingSystem os = ua.getOperatingSystem();
		if (DeviceType.MOBILE.equals(os.getDeviceType()))
		{
			return true;
		}
		return false;
	}
}
