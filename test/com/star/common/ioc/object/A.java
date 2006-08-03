
package com.star.common.ioc.object;

public class A {
	
	private String id;
	
	private int p1;
	
	private B b= null;

	public void init(){
		p1 = 20;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}
	
	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
