package com.star.common.richclient.factory;

import junit.framework.TestCase;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.richclient.Application;
import com.star.common.richclient.factory.composite.CompositeFactory;
import com.star.common.util.TreeMap;

public class CompositeFactoryTest extends TestCase {

	public static void main(String[] args) {
		
		TreeMap configTreeMap=new TreeMap();
		configTreeMap.load(
                StaticBeanFactory.DEFAULT_INTERPRETER_CONFIG_FILE,
                "/com/star/common/richclient/factory/bean-config2.properties");
		
		DefaultBeanFactory context = new DefaultBeanFactory(configTreeMap);
		CompositeFactory layout3 = context.getBean("layout3",CompositeFactory.class);
		
		Application app = new Application();
		app.setContentsFactory(layout3);
		app.open();
		
	}

}
