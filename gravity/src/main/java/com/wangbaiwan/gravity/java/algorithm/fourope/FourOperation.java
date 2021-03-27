package com.wangbaiwan.gravity.java.algorithm.fourope;


import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 实现四则运算
 *
 * @author wangbaiwan
 */
public class FourOperation
{
	public static void main(String[] args) throws ScriptException
	{
		String exp = " 1 + ( 2 * 1234 ) * ( 8 / 2) ";
		int i = FourOperation.evaluateExpression(exp);
		System.out.println("手动解析结果: " + i);

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine jsEngine = manager.getEngineByName("js");
		Object eval = jsEngine.eval(exp);
		System.out.println("ScriptEngine 结果: " + eval);

		JexlEngine jexlEngine = new JexlBuilder().create();
		JexlExpression jexlExpression = jexlEngine.createExpression(exp);
		Object evaluate = jexlExpression.evaluate(null);
		System.out.println("org.apache.commons commons-jexl3: " + evaluate);
	}

	/**
	 * Operands 操作数
	 * Operator 操作符 #用于表示开头和结尾, 以免操作符和空字符对比, 没有意义
	 *
	 * @param expression 字符串表达式
	 */
	public static int evaluateExpression(String expression)
	{
		// 初始化, #标识表达式开头和结尾
		expression = expression + "#";
		// 操作数栈
		Stack<Integer> operandStack = new Stack<>();
		// 操作符栈
		Stack<String> operatorStack = new Stack<>();
		//  #标识表达式开头
		operatorStack.push("#");
		String[] charArray = FourOperation.analyzeExp(expression);
		int i = 0;
		String item = charArray[0];
		while (!"#".equals(item) || !"#".equals(operatorStack.peek()))
		{
			++i;
			// 是否是操作数, 是则入栈
			if (!FourOperation.isOperator(item) && !"#".equals(item))
			{
				operandStack.push(Integer.parseInt(item));
				item = charArray[i];
				continue;
			}
			// 栈顶操作符和当前操作符的优先级
			char precede = precede(operatorStack.peek(), item);
			switch (precede)
			{
				case '<':
					operatorStack.push(item);
					item = charArray[i];
					break;
				case '=':
					operatorStack.pop();
					item = charArray[i];
					break;
				case '>':
					Integer operand1 = operandStack.pop();
					Integer operand2 = operandStack.pop();
					String operator = operatorStack.pop();
					operandStack.push(FourOperation.operate(operator, operand2, operand1));
					--i;
					break;
				default:
			}
		}
		return operandStack.pop();
	}

	/**
	 * 表达式分析, 提取其中的数字和操作符
	 *
	 * @param expression
	 * @return
	 */
	private static String[] analyzeExp(String expression)
	{
		List<String> result = new ArrayList<>();
		StringBuilder curInt = new StringBuilder();
		for (char c : expression.toCharArray())
		{
			if (c == ' ')
			{
				continue;
			}
			String item = String.valueOf(c);
			if ("#".equals(item))
			{
				result.add(item);
				continue;
			}
			if (FourOperation.isOperator(item))
			{
				if (curInt.length() > 0)
				{
					result.add(curInt.toString());
					curInt.delete(0, curInt.length());
				}
				result.add(item);
				continue;
			}
			curInt.append(item);
		}
		String[] arr = new String[result.size()];
		result.toArray(arr);
		return arr;
	}


	/**
	 * ()*+-/ 字节顺序
	 * 或者使用Arrays.sort排序下
	 *
	 * @see Arrays#sort(short[])
	 */
	private static final List<String> OPERATOR = Arrays.asList("(", ")", "*", "+", "-", "/");

	/**
	 * 是否是操作符
	 * <p>
	 *
	 * @param b 要判断的额字节
	 * @apiNote
	 */
	private static boolean isOperator(String b)
	{
		return OPERATOR.contains(b);
	}

	/**
	 * 判断操作符优先级
	 * 若两个+相遇, 表明栈顶的+, 可以做运算了, 这也是把栈顶操作符放在第一位置的原因
	 *
	 * @return 指定字符, 如下
	 * > c1优先级大于c2
	 * = c1优先级等于c2, 即()
	 * < c1优先级小于c2
	 * 优先级表：
	 * <<<<<+   -   *   /   (   )   #
	 * +    >   >   <   <   <   >   >
	 * -    >   >   <   <   <   >   >
	 * *    >   >   >   >   <   >   >
	 * /    >   >   >   >   <   >   >
	 * (    <   <   <   <   <   =
	 * )    >   >   >   >       >   >
	 * #    <   <   <   <   <       =
	 */
	private static char precede(String c1, String c2)
	{
		// #
		if ("#".equals(c1))
		{
			if ("#".equals(c2))
			{
				return '=';
			}
			else
			{
				return '<';
			}
		}
		// )
		if (")".equals(c1))
		{
			return '>';
		}
		// (
		if ("(".equals(c1))
		{
			if (")".equals(c2))
			{
				return '=';
			}
			else
			{
				return '<';
			}
		}
		// */
		c1 = "/".equals(c1) ? "*" : c1;
		if ("*".equals(c1) && "(".equals(c2))
		{
			return '<';
		}
		// +-
		c1 = "-".equals(c1) ? "+" : c1;
		if ("+".equals(c1) && Arrays.asList("*", "/", "(").contains(c2))
		{
			return '<';
		}
		else
		{
			return '>';
		}
	}

	/**
	 * 计算只有一个运算符的四则运算
	 * <p>
	 * 如: 计算 1+2, 结果为3
	 *
	 * @param operator +
	 * @param operand1 1
	 * @param operand2 2
	 * @return 结果
	 */
	private static int operate(String operator, Integer operand1, Integer operand2)
	{
		if ("+".equals(operator))
		{
			return operand1 + operand2;
		}
		if ("-".equals(operator))
		{
			return operand1 - operand2;
		}
		if ("*".equals(operator))
		{
			return operand1 * operand2;
		}
		if ("/".equals(operator))
		{
			return operand1 / operand2;
		}
		return 0;
	}
}
