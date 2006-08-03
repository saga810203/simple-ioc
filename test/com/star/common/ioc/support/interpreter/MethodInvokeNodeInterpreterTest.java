package com.star.common.ioc.support.interpreter;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

import junit.framework.TestCase;

public class MethodInvokeNodeInterpreterTest extends TestCase {
	public void test1(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		Object getP1 = context.getBean("getP1");
		assertEquals("abc",getP1);

		Object setP1 = context.getBean("setP1");
		assertNull(setP1);
		
		getP1 = context.getBean("getP1");
		assertEquals("def",getP1);
		
	}
	
	public void test2(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/ioc/support/interpreter/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		//(string)zhangsan => [zhangsan,string]
		String[] split1 = context.getBean("split1",String[].class);
		assertEquals("zhangsan",split1[0]);
		assertEquals("string",split1[1]);
		
	}
}
