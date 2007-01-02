package com.star.common.richclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import junit.framework.TestCase;

import com.star.common.util.TreeMap;
import com.star.sms.richclient.Launch;

public class PickleMakeTest extends TestCase {
	TreeMap treeMap;
	@Override
	protected void setUp() throws Exception {

		treeMap = new TreeMap();
		treeMap.load(Launch.class, "bean-config-app.properties");

	}

	public void testA(){
		long start = System.currentTimeMillis();
		
		int n = 10000;
		Object o = treeMap;
		for (int i = 0; i < n; i++) {
			o = deepClone(o);
		}
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000L);
	}
	
	public void testB(){
		long start = System.currentTimeMillis();
		
		int n = 10000;
		Object o = new Date();
		for (int i = 0; i < n; i++) {
			o = deepClone(o);
		}
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000L);
	}
	
	public static Object deepClone(Object obj) {
		if(obj == null) {
			return null;
		}
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (oi.readObject());
		}
        catch(java.io.NotSerializableException e) {
            throw new IllegalArgumentException(e.getMessage() + " must implements 'Serializable' interface.",e);
        }
		catch(IOException e) {
			throw new IllegalArgumentException(e.getMessage(),e);
		}
		catch(ClassNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage(),e);
		}
	}
}
