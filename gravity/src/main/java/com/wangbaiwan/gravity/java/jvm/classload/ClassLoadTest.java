package com.wangbaiwan.gravity.java.jvm.classload;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * 自定义类加载器
 *
 * @author eyeoftwilight
 */
public class ClassLoadTest
{
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		ClassLoader myClassLoader = new ClassLoader()
		{
			/**
			 * Loads the class with the specified <a href="#binary-name">binary name</a>.
			 * This method searches for classes in the same manner as the {@link
			 * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
			 * machine to resolve class references.  Invoking this method is equivalent
			 * to invoking {@link #loadClass(String, boolean) loadClass(name,
			 * false)}.
			 *
			 * @param name The <a href="#binary-name">binary name</a> of the class
			 * @return The resulting {@code Class} object
			 * @throws ClassNotFoundException If the class was not found
			 */
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException
			{
				String fileName = name.substring(name.lastIndexOf(".")) + "class";
				InputStream inputStream = getClass().getResourceAsStream(fileName);
				if (inputStream == null)
				{
					return super.loadClass(name);
				}
				try
				{
					byte[] b = new byte[inputStream.available()];
					inputStream.read(b);
					return defineClass(name, b, 0, b.length);
				}
				catch (IOException e)
				{
					throw new ClassNotFoundException();
				}
			}
		};

		Object obj = myClassLoader.loadClass("com.wangbaiwan.java.jvm.classload.ClassLoadTest").getDeclaredConstructor().newInstance();
		System.out.println(obj.getClass());
		System.out.println(obj instanceof ClassLoader);
	}
}
