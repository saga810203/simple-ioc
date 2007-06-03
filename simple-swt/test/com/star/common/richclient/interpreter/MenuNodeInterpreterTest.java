package com.star.common.richclient.interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.util.Node;

public class MenuNodeInterpreterTest {

	@Test
	public void testDoCreateObject() {
		
		StaticBeanFactory.init(MenuNodeInterpreterTest.class,"appmenu.properties");
		Node node=(Node)StaticBeanFactory.getBean("menuBar");

		assertEquals(3, node.getChildren().size());
		assertEquals(3, node.findChildByKey("file").getChildren().size());
		assertNotNull(node.findChildByKey("file").findChildByKey("b"));
		assertNotNull(node.findChildByKey("file").findChildByKey("c"));
		assertNotNull(node.findChildByKey("help"));
		
		assertEquals(1, node.findChildByKey("file2").getChildren().size());
		
	}

}
