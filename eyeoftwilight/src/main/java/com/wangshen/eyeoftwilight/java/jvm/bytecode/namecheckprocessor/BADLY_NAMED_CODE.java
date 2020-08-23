package com.wangshen.eyeoftwilight.java.jvm.bytecode.namecheckprocessor;

/**
 * 命名规范的“反面教材”测试代码
 *
 * @author eyeoftwilight
 */
public class BADLY_NAMED_CODE
{
	enum colors
	{
		red, blue, green;
	}

	static final int _FORTY_TWO = 42;

	public static int NOT_A_CONSTANT = _FORTY_TWO;

	protected void BADLY_NAMED_CODE()
	{
		return;
	}

	public void NOTcamelCASEmethodNAME()
	{
	}
}
