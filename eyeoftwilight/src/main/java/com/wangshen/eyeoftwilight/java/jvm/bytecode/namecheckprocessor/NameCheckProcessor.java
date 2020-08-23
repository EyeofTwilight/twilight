package com.wangshen.eyeoftwilight.java.jvm.bytecode.namecheckprocessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 驼峰名称检测
 * 只支持JDK 11的代码
 * 用 * 表示支持所有的Annotations
 *
 * @author eyeoftwilight
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor
{

	/**
	 * {@inheritDoc}
	 *
	 * @param annotations
	 * @param roundEnv
	 */
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{

		return false;
	}
}
