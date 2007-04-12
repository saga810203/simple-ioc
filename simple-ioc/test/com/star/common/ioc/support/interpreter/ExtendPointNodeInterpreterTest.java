package com.star.common.ioc.support.interpreter;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.object.D;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

public class ExtendPointNodeInterpreterTest extends TestCase {
	public void test1(){
		
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		
		TreeMap configTreeMap2=new TreeMap();
		configTreeMap.load(getClass(),"bean-config3.properties");
		DefaultBeanFactory childContext = new DefaultBeanFactory(configTreeMap2);
		childContext.setParent(context);
		
		D d = context.getBean("i",D.class);
		
		assertEquals(3,d.getP9().size());
		assertEquals("a",d.getP9().get(0));
		assertEquals("b",d.getP9().get(1));
		assertEquals("c",d.getP9().get(2));
	}
}
