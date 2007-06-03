package com.star.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ê÷µÄ½Úµã¡£
 * 
 * @see TreeMap
 * @author liuwei
 * @version 1.0
 */
public class Node implements Serializable,Cloneable{
	
	private static final long serialVersionUID = 1L;

	private String key;

	private String value;

	private List<Node> children = new ArrayList<Node>();
	
	private Node parent;
	
	//=====================
	
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
		return "Node{key:" + key + ", value:" + value + "}";
	}

	public Node clone(){
		Node node = null;
		try {
			node = (Node)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		node.parent = null;
		node.children = new ArrayList<Node>();
		for (Node child : children) {
			Node c =child.clone();
			node.addChild(c);
		}
		return node;
	}
	
	//=============================
	
	public boolean addChild(Node o) {
		o.parent = this;
		return children.add(o);
	}

	public void setChildren(List<Node> children) {
		this.children.clear();
		if (children != null)
			for (Node c : children) {
				addChild(c);
			}
	}

	//=====================

	public List<Node> getChildren() {
		return children;
	}

	public Node getParent() {
		return parent;
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
	
	public void setValue(String value) {
		this.value = value;
	}
		
}