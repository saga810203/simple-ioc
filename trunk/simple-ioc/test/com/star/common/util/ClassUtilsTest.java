package com.star.common.util;

import junit.framework.TestCase;

public class ClassUtilsTest extends TestCase {
	public void test1(){
		Class c = ClassUtils.getClassForName("int");
		assertEquals(int.class,c);
	}
}
