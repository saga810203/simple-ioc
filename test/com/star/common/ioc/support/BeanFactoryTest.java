
package com.star.common.ioc.support;

import junit.framework.TestCase;

import com.star.common.ioc.object.A;
import com.star.common.ioc.object.B;
import com.star.common.ioc.object.C;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.TreeMap;

public class BeanFactoryTest extends TestCase {
	
	
	public void testBeanCreate(){
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		A a=(A)context.getBean("a1");
		assertNotNull(a);
		assertEquals("a1",a.getId());
		assertNull(a.getB());
		assertEquals(0,a.getP1());
	}
	
	
	public void testInject(){
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		A a=(A)context.getBean("a2");
		assertNotNull(a.getB());
	}
	
	public void testInit(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		A a=(A)context.getBean("a3");
		assertEquals(20,a.getP1());
	}
	
	public void testFactoryBean(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		Integer cA=(Integer)context.getBean("c1");
		assertNotNull(cA);
		Integer cB=(Integer)context.getBean("c1");
		assertNotSame(cA,cB);
	}
	
	public void testRef(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		Integer c=(Integer)context.getBean("c2");
		assertNotNull(c);
	}
	
	public void testFactoryRef(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		C c=(C)context.getBean("c3");
		assertNotNull(c);
	}
	
	public void testNoSingleton(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		B bA=(B)context.getBean("b1");
		B bB=(B)context.getBean("b1");
		assertNotSame(bA,bB);
	}
	
	public void testNoSingletonWithRef(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		B bA=(B)context.getBean("b2");
		B bB=(B)context.getBean("b2");
		assertNotSame(bA,bB);
	}
	
	public void testNoSingletonWithRefFix(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		B bA=(B)context.getBean("b3");
		B bB=(B)context.getBean("b3");
		assertNotSame(bA,bB);
	}
	
	public void testNoSingletonWithFactroyBean(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		
		C cA=(C)context.getBean("(&ref)c1");
		C cB=(C)context.getBean("(&ref)c1");
		assertNotNull(cA);
		assertSame(cA,cB);
		
		C cC=(C)context.getBean("(&ref)c4");
		C cD=(C)context.getBean("(&ref)c4");
		assertNotNull(cA);
		assertNotSame(cC,cD);
	}
	
	
	public void testEllipsis(){
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                
                "/com/star/common/ioc/support/bean-config.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);

		assertNotNull(context.getBean("a4"));
		assertNotNull(context.getBean("a5"));
	}

}
