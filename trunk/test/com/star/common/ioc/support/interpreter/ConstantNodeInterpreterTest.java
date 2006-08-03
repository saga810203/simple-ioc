package com.star.common.ioc.support.interpreter;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

import junit.framework.TestCase;

public class ConstantNodeInterpreterTest extends TestCase {
	public void test1(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		Integer max = context.getBean("max",Integer.class);
		
		assertEquals(Integer.MAX_VALUE,max.intValue());

	}
}
