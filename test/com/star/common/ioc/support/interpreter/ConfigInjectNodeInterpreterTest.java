package com.star.common.ioc.support.interpreter;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.object.D;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

public class ConfigInjectNodeInterpreterTest extends TestCase {
	public void test1(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		D d = context.getBean("d1",D.class);
		
		assertEquals("d1.p1",d.getP1());

	}
}
