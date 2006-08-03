package com.star.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Class帮助类
 * 
 * @author liuwei
 * @version 1.0
 */
public class ClassUtils {

	private static final Map<String, Class> MAP = new HashMap<String, Class>();

	static {
		MAP.put("void", void.class);
		MAP.put("int", int.class);
		MAP.put("long", long.class);
		MAP.put("boolean", boolean.class);
		MAP.put("byte", byte.class);
		MAP.put("char", char.class);
		MAP.put("float", float.class);
		MAP.put("double", double.class);
	}

	/**
	 * 通过类名得到类。
	 * 
	 * @param className
	 *            类名
	 * @return
	 */
	public static Class getClassForName(String className) {
		Class c = MAP.get(className);
		if(c!=null)return c;
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
