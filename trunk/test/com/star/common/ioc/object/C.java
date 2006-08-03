
package com.star.common.ioc.object;

import java.util.Random;

public class C {
	
	private Random r = new Random();
	
	public Integer getBean(){
		return new Integer(r.nextInt());
	}
}
