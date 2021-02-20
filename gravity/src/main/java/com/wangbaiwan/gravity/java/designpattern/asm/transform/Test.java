package com.wangbaiwan.gravity.java.designpattern.asm.transform;

import javassist.bytecode.ClassFilePrinter;
import org.springframework.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Test
{
	public static void main(String[] args) throws IOException
	{

		ClassReader classReader = new ClassReader(
				Objects.requireNonNull(ClassFilePrinter.class.getClassLoader()
						.getResourceAsStream("com/wangshen/eyeoftwilight/java/designpattern/asm/transform/TransformA.class"))
		);
		ClassWriter classWriter = new ClassWriter(0);
		ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM4, classWriter)
		{
			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions)
			{
				MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
				// 只对方法aa使用
				if ("aa".equals(name))
				{
					methodVisitor = new MethodVisitor(Opcodes.ASM4, methodVisitor)
					{
						@Override
						public void visitCode()
						{
							// 调用 静态方法 类TransformB 的bb void 方法
							visitMethodInsn(Opcodes.INVOKESTATIC, "TransformB", "bb", "()V", false);
							super.visitCode();
						}
					};
				}
				return methodVisitor;
			}
		};


		classReader.accept(classVisitor, 0);
		byte[] bytes = classWriter.toByteArray();

		String userDir = (String) System.getProperties().get("user.dir");
		File file = new File(userDir + "/com/wangshen/eyeoftwilight");
		file.mkdirs();
		FileOutputStream fo = new FileOutputStream(userDir + "/com/wangshen/eyeoftwilight/TransformA_0.class");
		fo.write(bytes);
		fo.flush();
		fo.close();
	}
}
