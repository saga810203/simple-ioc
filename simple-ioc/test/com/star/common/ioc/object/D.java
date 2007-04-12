package com.star.common.ioc.object;

import java.util.List;

import com.star.common.util.Node;

public class D {
	
//	@string=com.star.common.ioc.support.interpreter.NodeInterpreters$StringNodeInterpreter
//	@int=com.star.common.ioc.support.interpreter.NodeInterpreters$IntNodeInterpreter
//	@long=com.star.common.ioc.support.interpreter.NodeInterpreters$LongNodeInterpreter
//	@float=com.star.common.ioc.support.interpreter.NodeInterpreters$FloatNodeInterpreter
//	@boolean=com.star.common.ioc.support.interpreter.NodeInterpreters$BooleanNodeInterpreter
//	@class=com.star.common.ioc.support.interpreter.NodeInterpreters$ClassNodeInterpreter
//	@null=com.star.common.ioc.support.interpreter.NodeInterpreters$NullNodeInterpreter
//	@node=com.star.common.ioc.support.interpreter.NodeInterpreters$NodeNodeInterpreter

	
	private String p1;
	
	private int p2;
	
	private long p3;
	
	private float p4;
	
	private boolean p5;
	
	private Class p6;
	
	private String p7 = "abc";
	
	private Node p8 ;

	private List<String> p9;
	
	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public int getP2() {
		return p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}

	public long getP3() {
		return p3;
	}

	public void setP3(long p3) {
		this.p3 = p3;
	}

	public float getP4() {
		return p4;
	}

	public void setP4(float p4) {
		this.p4 = p4;
	}

	public boolean isP5() {
		return p5;
	}

	public void setP5(boolean p5) {
		this.p5 = p5;
	}

	public Class getP6() {
		return p6;
	}

	public void setP6(Class p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	public Node getP8() {
		return p8;
	}

	public void setP8(Node p8) {
		this.p8 = p8;
	}

	public List<String> getP9() {
		return p9;
	}

	public void setP9(List<String> p9) {
		this.p9 = p9;
	}
	
}
