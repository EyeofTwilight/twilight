/**
 * 身份证框 失焦时触发,只验证这一个元素
 */
function validIdNumber()
{
	//只验证idNumber一个元素，验证通过返回true，失败返回false
	var result = validateForm().element($("#idNumber"));
	if (result)
	{
		// 验证通过，则再去查数据库中是否已经存在了这个身份证号
	}
}

/**
 * 保存
 */
function save()
{
	// 首先验证表单元素，
	// 若不是通过submit方式提交,则建议在from元素中添加:onsubmit="return false"
	if (!validateForm().form())
	{
		return
	}
}

/**
 * 验证表单
 */
function validateForm()
{
	return $("#form").validate({
		rules: {
			idNumber: "isIdNumber",// 指定自定义验证的名字
			userName: {required: true}
		},
		messages: {
			userName: {required: "请输入用户名"}
		}
	});
}

// 自定义身份证验证，isIdNumber是这个自定义验证的名字
$.validator.addMethod("isIdNumber", function (value, element)
{

	var result = isIdNumber(value);
	return result;
}, "请填写正确的身份证号");


/**
 * 验证是否是身份证号码
 * @param num
 * @returns {boolean}
 */
function isIdNumber(num)
{
	// 去除字符串内所有的空格,g表示匹配全部(即替换全部)
	num = num.replace(/\s*/g, "");
	num = num.toUpperCase();

	//身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。   
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
	{
		return false;
	}
	//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
	//下面分别分析出生日期和校验位 
	var len, re;
	len = num.length;
	if (len == 15)
	{
		re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
		var arrSplit = num.match(re);

		//检查生日日期是否正确 
		var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]);
		var bCorrectDay;
		bCorrectDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) &&
			(dtmBirth.getDate() == Number(arrSplit[4]));
		if (!bCorrectDay)
		{
			return false;
		}
		else
		{
			//将15位身份证转成18位 
			//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var nTemp = 0,
				i;
			num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
			for (i = 0; i < 17; i++)
			{
				nTemp += num.substr(i, 1) * arrInt[i];
			}
			num += arrCh[nTemp % 11];
			return true;
		}
	}

	if (len == 18)
	{
		re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
		var arrSplit = num.match(re);

		//检查生日日期是否正确 
		var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]);
		var bCorrectDay;
		bCorrectDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) &&
			(dtmBirth.getDate() == Number(arrSplit[4]));
		if (!bCorrectDay)
		{
			return false;
		}
		else
		{
			//检验18位身份证的校验码是否正确。 
			//校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
			var valnum;
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
			var nTemp = 0,
				i;
			for (i = 0; i < 17; i++)
			{
				nTemp += num.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[nTemp % 11];
			if (valnum != num.substr(17, 1))
			{
				return false;
			}
			return true;
		}
	}
	return false;
}