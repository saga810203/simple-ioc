package com.star.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 树的节点。
 * 
 * @see TreeMap
 * @author liuwei
 * @version 1.0
 */
public class Node implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String key;

	private String value;
	//节点所属层次
	private int lay;

	private List<Node> children = new ArrayList<Node>();
	
	private Node parent;
	
	//=====================
	
	public static final int VALUE_CACHE = 0;//"value_cache";

	public static final int OTHER_CACHE = 1;
	
	private static final Object NULL = new Serializable(){};
	
	private Object[] datas = new Object[2];
	
	//private Map<Object, Object> datas = new HashMap<Object, Object>();

	//=====================
	
	public Node(){
		Arrays.fill(datas,NULL);
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String id) {
		this.key = id;
	}

	public String getValue() {
		return value;
	}

	public int getLay() {
		return lay;
	}

	public void setLay(int lay) {
		this.lay = lay;
	}

	public void setChildren(List<Node> children) {
		this.children.clear();
		if (children != null)
			for (Node c : children) {
				addChild(c);
			}
	}

	public List<Node> getChildren() {
		return children;
	}

	public Node getParent() {
		return parent;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKeyPath() {
		StringBuilder b = new StringBuilder();
		b.append(key);
		Node node = this;
		while ((node = node.parent) != null) {
			b.insert(0, node.key);
			b.insert(node.key.length(), '.');
		}
		return b.toString();
	}

	public Node findChildByKey(String key) {
		if (children != null && key != null)
			for (Node child : children) {
				if (key.equals(child.getKey())) {
					return child;
				}
			}
		return null;
	}

	public String toString() {
		return "Node{key:" + key + ", value:" + value + ", lay:" + lay + "}";
	}
	
	//=============================
	
	public boolean addChild(Node o) {
		o.parent = this;
		return children.add(o);
	}

	public void putData(int k, Object v) {
		//datas.put(k, v);
		datas[k]=v;
	}

	public Object removeData(int k) {
		//return datas.remove(k);
		Object v = getData(k);
		datas[k]=NULL;
		return v;
	}

	public Object getData(int k) {
		//return datas.get(k);
		return datas[k]==NULL?null:datas[k];
	}

	public boolean containsDataKey(int k) {
		//return datas.containsKey(k);
		return datas[k]!=NULL;
	}
}