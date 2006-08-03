package com.star.common.ioc.support.interpreter;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

public class ArrayNodeInterpreterTest extends TestCase {
	public void test1(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		Object[] h1 = context.getBean("h1",Object[].class);
		assertEquals(2,h1.length);
		assertEquals("10",h1[0]);
		assertEquals("20",h1[1]);
		
		int[] h2 = context.getBean("h2",int[].class);
		assertEquals(2,h2.length);
		assertEquals(10,h2[0]);
		assertEquals(20,h2[1]);
		
	}
}
