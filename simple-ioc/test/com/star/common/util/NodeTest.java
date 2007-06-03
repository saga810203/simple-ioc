package com.star.common.util;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class NodeTest {

	@Test
	public void testClone() {
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("child","childValue");
		b.addChild("childchild","childchildValue");
		
		Node c = b.getTreeMap().get("root").findChildByKey("child");
		Node clone = c.clone();
		
		assertNotSame(c,clone);
		assertNotSame(c.getChildren(), clone.getChildren());
		assertNotSame(c.getChildren().get(0), clone.getChildren().get(0));
		assertNull(clone.getParent());
	}

}
