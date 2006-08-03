package com.star.common.util;

import junit.framework.TestCase;

public class TreeMapTest extends TestCase {
	public void testA(){
		TreeMap t=new TreeMap();
		t.load(TreeMapTest.class,"tree1.properties");
		
		System.out.println(t);
		assertEquals(2,t.size());
		
		Node a = t.get("a");
		assertEquals(0,a.getChildren().size());
	}
	
	public void testB(){
		TreeMap t=new TreeMap();
		t.load(TreeMapTest.class,"tree2.properties");
		
		System.out.println(t);
		
		assertEquals(2,t.size());
		
		Node a = t.get("a");
		assertEquals("a",a.getKey());
		assertEquals("aa",a.getValue());
		assertEquals(0,a.getLay());
		assertEquals(2,a.getChildren().size());
		
		Node b = a.getChildren().get(0);
		assertEquals("b",b.getKey());
		assertEquals("bb",b.getValue());
		assertEquals(1,b.getLay());
		
		Node c = b.getChildren().get(0);
		assertEquals("c",c.getKey());
		assertEquals("cc",c.getValue());
		assertEquals(2,c.getLay());
		
		Node d = a.getChildren().get(1);
		assertEquals("d",d.getKey());
		assertEquals("dd",d.getValue());
		assertEquals(1,d.getLay());
		
		Node e = t.get("e");
		assertEquals("e",e.getKey());
		assertEquals("ee",e.getValue());
		assertEquals(0,e.getLay());
		assertEquals(3,e.getChildren().size());
		
	}
	
	public void testInjectNode(){
		TreeMap t=new TreeMap();
		t.load(TreeMapTest.class,"tree3.properties");
		
		System.out.println(t);
		
		Node a = t.get("a");
		assertEquals(3,a.getChildren().size());
		
		Node a3 = a.getChildren().get(0);
		assertEquals("a3",a3.getKey());
		assertEquals(1,a3.getLay());
		
		Node a1 = a.getChildren().get(1);
		assertEquals("a1",a1.getKey());

		
		Node a2 = a.getChildren().get(2);
		assertEquals("a2",a2.getKey());
		assertEquals("a2Value",a2.getValue());
		
		assertEquals(1,a2.getChildren().size());
		Node a21 = a2.getChildren().get(0);
		assertEquals("a21",a21.getKey());
		
		assertEquals(1,a1.getChildren().size());
		Node a11 = a1.getChildren().get(0);
		assertEquals("a11",a11.getKey());
		
	}
	
}
