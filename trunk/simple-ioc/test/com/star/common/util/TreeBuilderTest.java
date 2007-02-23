
package com.star.common.util;

import java.util.LinkedHashMap;

import com.star.common.util.TreeBuilder.AddSiblingType;

import junit.framework.TestCase;

/**
 * @author myace
 *
 */
public class TreeBuilderTest extends TestCase{

	
	public void testAddRootNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addRoot("root2","rootValue2");
		LinkedHashMap<String, Node> map = b.getTreeMap();
		Node n = map.get("root");
		assertEquals("root",n.getKey());
		assertEquals("rootValue",n.getValue());
		n = map.get("root2");
		assertEquals("root2",n.getKey());
		assertEquals("rootValue2",n.getValue());
		
	}
	

	public void testAddChildNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("child","childValue");
		b.addChild("childchild","childchildValue");
		LinkedHashMap<String, Node> map = b.getTreeMap();
		
		assertEquals(1,map.size());

		Node n = map.get("root");
		assertEquals("root",n.getKey());
		assertEquals("rootValue",n.getValue());
		
		Node cn =n.getChildren().get(0);
		assertEquals("child",cn.getKey());
		assertEquals("childValue",cn.getValue());
		
		Node ccn =cn.getChildren().get(0);
		assertEquals("childchild",ccn.getKey());
		assertEquals("childchildValue",ccn.getValue());
	}
	
	public void testAddSiblingNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("child","childValue");
		b.addSibling("child2","child2Value");
		LinkedHashMap<String, Node> map = b.getTreeMap();
		
		assertEquals(1,map.size());

		Node n = map.get("root");
		assertEquals("root",n.getKey());
		assertEquals("rootValue",n.getValue());
		
		Node cn =n.getChildren().get(0);
		assertEquals("child",cn.getKey());
		assertEquals("childValue",cn.getValue());
		
		Node cn2 =n.getChildren().get(1);
		assertEquals("child2",cn2.getKey());
		assertEquals("child2Value",cn2.getValue());
	}
	
	public void testAddMoreNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("child","childValue");
		b.addChild("childchild","childchildValue");
		b.addToParent("child2","child2Value",2);
		LinkedHashMap<String, Node> map = b.getTreeMap();
		
		assertEquals(1,map.size());

		Node n = map.get("root");
		assertEquals("root",n.getKey());
		assertEquals("rootValue",n.getValue());
		
		Node cn =n.getChildren().get(0);
		assertEquals("child",cn.getKey());
		assertEquals("childValue",cn.getValue());
		
		Node ccn =cn.getChildren().get(0);
		assertEquals("childchild",ccn.getKey());
		assertEquals("childchildValue",ccn.getValue());
		
		Node cn2 =n.getChildren().get(1);
		assertEquals("child2",cn2.getKey());
		assertEquals("child2Value",cn2.getValue());
	}
		
	public void testAddErrorLayChildNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		try {
			b.addToParent("child","childValue",1);
			fail();
		} catch (NodeException e) {
			;
		}
	}
	
	public void testAddNullKeyChildNode(){
		TreeBuilder b = new TreeBuilder();
		try {
			b.addRoot(null,"rootValue");
			fail();
		} catch (NodeException e) {
			;
		}
		b.addRoot("root2","rootValue2");
		b.addChild(null,"xxxx");
	}
	
	public void testFindNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("c1","v1");
		b.addChild("c11","v11");
		b.addChild("c2","v2","root");
		b.addChild("c21","v21");
		b.addRoot("root2","rootValue2");
		
		Node root2 = b.findNode("root2");
		assertEquals("rootValue2",root2.getValue());
		
		Node c21 = b.findNode("root","c2","c21");
		assertEquals("v21",c21.getValue());
		
		Node noExist = b.findNode("root","c2","noExist");
		assertNull(noExist);
		
	}
	
	public void testAddNodeToNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("c1","v1");
		b.addChild("c11","v11");
		b.addChild("c2","v2","root");
		
		b.addChildTo("c12","v12","root.c1");
		
		LinkedHashMap<String, Node> map = b.getTreeMap();
		Node n = map.get("root");
		Node c1 =n.getChildren().get(0);
		Node c12 =c1.getChildren().get(1);
		
		assertEquals("c12",c12.getKey());
		
		
		b.addChild("c121","v121");
		
		Node c121 =c12.getChildren().get(0);
		assertEquals("c121",c121.getKey());
		
		
		b.addChildTo("c10","v10","root.c1",0);
		Node c10 =c1.getChildren().get(0);
		assertEquals("c10",c10.getKey());
	}
	
	/**
	 * 加入兄弟节点到指定节点.
	 */
	public void testAddSiblingNodeToNode(){
		TreeBuilder b = new TreeBuilder();
		b.addRoot("root","rootValue");
		b.addChild("child","childValue");
		b.addSiblingTo("child2","child2Value","root.child",AddSiblingType.backward);
		
		
		LinkedHashMap<String, Node> map = b.getTreeMap();

		Node n = map.get("root");
		Node cn =n.getChildren().get(0);
		assertEquals("child",cn.getKey());
		Node cn2 =n.getChildren().get(1);
		assertEquals("child2",cn2.getKey());
		assertEquals("child2Value",cn2.getValue());
	}
	
	public void testAddSiblingNodeToNodeFail(){
		try {
			TreeBuilder b = new TreeBuilder();
			b.addRoot("root","rootValue");
			b.addSiblingTo("child2","child2Value","root.child",AddSiblingType.backward);
			fail();
		} catch (NodeException e) {
			;
		}
	}
	
	public void testAddSiblingNodeToNodeFail2(){
			TreeBuilder b = new TreeBuilder();
			b.addRoot("root","rootValue");
			boolean s =b.addSiblingToAttempt("child2","child2Value","root.child",AddSiblingType.backward);
			assertEquals(false, s);
	}
	
}
