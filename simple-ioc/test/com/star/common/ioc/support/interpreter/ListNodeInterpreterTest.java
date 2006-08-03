package com.star.common.ioc.support.interpreter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

public class ListNodeInterpreterTest extends TestCase {
	public void test1(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		List f1 = context.getBean("f1",ArrayList.class);
		assertEquals(2,f1.size());
		assertEquals(new Integer(10),f1.get(0));
		assertEquals(new Integer(20),f1.get(1));
		
		List f2 = context.getBean("f2",LinkedList.class);
		assertEquals(2,f2.size());
		assertEquals(new Integer(10),f2.get(0));
		assertEquals(new Integer(20),f2.get(1));
		
	}
}
