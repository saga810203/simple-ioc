package com.star.common.util;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	public void testInterpreteConfigStringA() {
		//(string)zhangsan => [zhangsan,string]
		String s = "(string)zhangsan";
		String[] sc = StringUtils.split1(s);
		assertEquals("zhangsan",sc[0]);
		assertEquals("string",sc[1]);
	}
	
	public void testInterpreteConfigStringB() {
		//zhangsan => [zhangsan,null]
		String s = "zhangsan";
		String[] sc = StringUtils.split1(s);
		assertEquals("zhangsan",sc[0]);
		assertEquals(null,sc[1]);
	}
	
	public void testInterpreteConfigString2A() {
		 //id(BeanId) => [id,BeanId]
		String s = "id(BeanId)";
		String[] sc = StringUtils.splite2(s);
		assertEquals("id",sc[0]);
		assertEquals("BeanId",sc[1]);
		
	}
	
	public void testInterpreteConfigString2B() {
		//id => [id,null]
		String s = "id";
		String[] sc = StringUtils.splite2(s);
		assertEquals("id",sc[0]);
		assertEquals(null,sc[1]);
	}
}
