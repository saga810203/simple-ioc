package com.star.common.ioc.support.interpreter;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

public class MapNodeInterpreterTest extends TestCase {
	public void test1(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		Map g1 = context.getBean("g1",HashMap.class);
		assertEquals(2,g1.size());
		assertEquals("x",g1.get(new Integer(1)));
		assertEquals("y",g1.get(new Integer(2)));
		
		context.getBean("g2",java.util.TreeMap.class);
	
	}
}
