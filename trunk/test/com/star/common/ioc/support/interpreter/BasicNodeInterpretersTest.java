package com.star.common.ioc.support.interpreter;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.object.D;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.Node;
import com.star.common.util.TreeMap;

public class BasicNodeInterpretersTest extends TestCase {
	public void testNodeInterpreters(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		D d = context.getBean("d",D.class);
		
		assertEquals("abc",d.getP1());
		assertEquals(10,d.getP2());
		assertEquals(10L,d.getP3());
		assertEquals(10F,d.getP4());
		assertEquals(true,d.isP5());
		assertEquals(D.class,d.getP6());
		assertNull(d.getP7());
		assertEquals(Node.class,d.getP8().getClass());
		assertEquals("p81",d.getP8().getChildren().get(0).getKey());
	}
}
