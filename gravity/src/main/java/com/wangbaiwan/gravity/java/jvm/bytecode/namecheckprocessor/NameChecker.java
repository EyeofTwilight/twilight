package com.wangbaiwan.gravity.java.jvm.bytecode.namecheckprocessor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * @author eyeoftwilight
 */
public class NameChecker
{
	private final Messager messager;

	public NameChecker(ProcessingEnvironment processingEnvironment)
	{
		this.messager = processingEnvironment.getMessager();
	}


	/**
	 * 名称检查器实现类，继承了JDK 8 中
	 */
	private class NameCheckScanner extends ElementScanner8<Void, Void>
	{
		/**
		 * 用于检查Java类
		 *
		 * @param e
		 * @param aVoid
		 * @return
		 */
		@Override
		public Void visitType(TypeElement e, Void aVoid)
		{
			this.scan(e.getTypeParameters(), aVoid);

			return super.visitType(e, aVoid);
		}

		/**
		 * 检查方法名是否合法
		 *
		 * @param e
		 * @param aVoid
		 * @return
		 */
		@Override
		public Void visitExecutable(ExecutableElement e, Void aVoid)
		{
			if (e.getKind() == ElementKind.METHOD)
			{
				Name name = e.getSimpleName();
				if (name.contentEquals(e.getEnclosingElement().getSimpleName()))
				{
					String msg = "一个普通方法" + name + "不应当与类名重复，避免与构造函数产生混淆";
					messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
				}
			}
			return super.visitExecutable(e, aVoid);
		}

		/**
		 * 检查变量名是否合法
		 *
		 * @param e
		 * @param aVoid
		 * @return
		 */
		@Override
		public Void visitVariable(VariableElement e, Void aVoid)
		{
			if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || this.judgeConstant(e))
			{
				this.checkAllCaps(e);
			}
			else
			{
				this.checkCamelCase(e, false);
			}
			return super.visitVariable(e, aVoid);
		}

		/**
		 * 判断一个变量是否是常量
		 * 1.类型为接口
		 * 2.字段修饰符全包含：public static final
		 *
		 * @param variable
		 * @return
		 */
		public boolean judgeConstant(VariableElement variable)
		{
			if (variable.getEnclosingElement().getKind() == ElementKind.INTERFACE)
			{
				return true;
			}
			if (variable.getKind() == ElementKind.FIELD && variable.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)))
			{
				return true;
			}
			return false;
		}

		/**
		 * @param e
		 * @param initialCaps true: 必须以大写字母开头，false：必须以小写字母开头
		 */
		private void checkCamelCase(Element e, boolean initialCaps)
		{
			String name = e.getSimpleName().toString();
			boolean previousUpper = false;
			boolean conventional = true;
			// 码点：位于Unicode字符集中的索引
			int firstCodePoint = name.codePointAt(0);
			if (Character.isUpperCase(firstCodePoint))
			{
				previousUpper = true;
				if (!initialCaps)
				{
					messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应当以小写字母开头", e);
					return;
				}
			}
			else if (Character.isLowerCase(firstCodePoint))
			{
				if (initialCaps)
				{
					messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应当以大写字母开头", e);
					return;
				}
			}
			else
			{
				conventional = false;
			}

			if (conventional)
			{
				int cp = firstCodePoint;
				// char表示一个代码单元,可能并不是一个字符，用两个字节表示，2byte = 16bit最大值65535
				// 当大于65535，则需要用两个字符即4个byte表示
				// int i = Character.charCount(cp) 表示从第二个字符开始检测,因为第一个上面已经检测过了
				for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp))
				{
					cp = name.codePointAt(i);
					if (Character.isUpperCase(cp))
					{
						if (previousUpper)
						{
							conventional = false;
							break;
						}
						previousUpper = true;
					}
					else
					{
						if (!previousUpper)
						{
							conventional = false;
							break;
						}
						previousUpper = false;
					}
				}
			}
			if (!conventional)
			{
				messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + "应当符合驼峰命名法(Camel Case Names)", e);
			}
		}


		/**
		 * 大写名称检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母(即匹配常量格式)
		 *
		 * @param e
		 */
		private void checkAllCaps(Element e)
		{
			String name = e.getSimpleName().toString();
			boolean convertion = true;
			int firstCodePoint = name.codePointAt(0);
			if (Character.isUpperCase(firstCodePoint))
			{
				boolean previousUnderscore = false;
				int cp = firstCodePoint;
				for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp))
				{
					cp = name.codePointAt(i);
					if (cp == (int) '_')
					{
						if (previousUnderscore)
						{
							convertion = false;
							break;
						}
						previousUnderscore = true;
					}
					else
					{
						previousUnderscore = false;
						if (Character.isUpperCase(cp) && !Character.isDigit(cp))
						{
							convertion = false;
							break;
						}
					}
				}
			}
			else
			{
				convertion = false;
			}

			if (!convertion)
			{
				messager.printMessage(Diagnostic.Kind.WARNING, "常量" + name + "应当全部以大写字母或下划线命名，并且以字母开头", e);
			}
		}
	}
}
