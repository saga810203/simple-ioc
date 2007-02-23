package com.star.common.util;

import java.util.LinkedHashMap;

/**
 * 节点树构建器.
 * 
 * @author myace
 * 
 */
public class TreeBuilder {

	private LinkedHashMap<String, Node> map;

	private Node currentNode;

	public TreeBuilder() {
		map = new LinkedHashMap<String, Node>();
	}

	public TreeBuilder(LinkedHashMap<String, Node> map) {
		this.map = map;
	}

	public void addRoot(String key, String value) {
		assertNotNull(key);
		if (map.containsKey(key)) {
			System.err.println("Node(key[" + key
					+ "]) has exist, old node is replaced.");
		}
		Node node = createNode(key, value);
		map.put(node.getKey(), node);
		setCurrentNode(node);
	}

	public void addChild(String key, String value) {
		Node node = createNode(key, value);
		currentNode.addChild(node);
		setCurrentNode(node);
	}

	public void addSibling(String key, String value) {
		addToParent(key, value, 1);
	}

	public void addChild(String key, String value, String parentKey) {
		Node parent = currentNode;
		while (true) {
			if (parent == null) {
				throw new NodeException("can't add key='" + key
						+ "' to parentKey=" + parentKey + ".");
			}
			if ((parent.getKey() == null && parentKey == null)
					|| parent.getKey().equals(parentKey)) {
				break;
			}
			parent = parent.getParent();
		}
		addChildTo(key, value, parent);
	}
	
	public void addToParent(String key, String value, int parentLay) {
		if (parentLay <= 0) {
			throw new NodeException("parentLay must >0. parentLay=" + parentLay);
		}

		Node parent = currentNode;
		while (parentLay-- > 0) {
			if (parent == null) {
				throw new NodeException("can't add key='" + key
						+ "' to parentLay=" + parentLay + ".");
			}
			parent = parent.getParent();
		}
		if (parent == null) {
			throw new NodeException("can't add key='" + key
					+ "' to parentLay=" + parentLay + ".");
		}
		addChildTo(key, value, parent);
	}


	private void addChildTo(String key, String value, Node parent) {
		Node node = createNode(key, value);
		parent.addChild(node);
		setCurrentNode(node);
	}

	private Node createNode(String key, String value) {
		Node n = new Node();
		n.setKey(key);
		n.setValue(value);
		return n;
	}

	public void addChildTo(String key, String value, String parentFullKey)
			throws NodeException {
		addChildTo(key, value, parentFullKey, -1);
	}

	/**
	 * 节点加入。
	 * @param key
	 * @param value
	 * @param parentFullKey
	 *            祖先节点键字符串.
	 * @param place
	 *            节点被加入到父节点列表中的位置.
	 */
	public void addChildTo(String key, String value, String parentFullKey, int place)
			throws NodeException {
		if (!addChildToAttempt(key, value, parentFullKey, place)) {
			throw new NodeException("can't find node whose key is "
					+ parentFullKey + ".");
		}
	}

	public boolean addChildToAttempt(String key, String value, String parentFullKey,
			int place) {
		if (parentFullKey == null) {
			throw new IllegalArgumentException("parentFullKey can't be null.");
		}
		Node parent = findNode(parentFullKey.split("\\."));
		if (parent == null) {
			return false;
		}
		addTo(parent, key, value, place);
		return true;
	}

	private void addTo(Node parent, String key, String value, int place) {
		if (parent == null) {
			throw new IllegalArgumentException("parent node can't be null.");
		}
		Node child = createNode(key, value);
		if (place < 0) {
			parent.addChild(child);
		} else {
			parent.getChildren().add(place, child);
		}
		setCurrentNode(child);
	}

	/**
	 * @see #addSiblingTo(String, String, String,
	 *      com.star.common.util.TreeBuilder.AddSiblingType)
	 */
	public void addSiblingTo(String key, String value, String siblingFullKey) {
		addSiblingTo(key, value, siblingFullKey, AddSiblingType.backward);

	}

	/**
	 * 加入兄弟节点到指定节点.
	 * @param key
	 *            被添加节点的键
	 * @param value
	 *            被添加节点的值
	 * @param siblingFullKey
	 *            祖先及兄弟节点键字符串.
	 * @param addType
	 *            添加方向
	 */
	public void addSiblingTo(String key, String value, String siblingFullKey,
			AddSiblingType addType) {
		if (!addSiblingToAttempt(key, value, siblingFullKey, addType)) {
			throw new NodeException("can't find node whose key is "
					+ siblingFullKey + ".");
		}
	}

	public boolean addSiblingToAttempt(String key, String value,
			String siblingFullKey, AddSiblingType addType) {
		if (siblingFullKey == null) {
			throw new IllegalArgumentException("siblingFullKey can't be null.");
		}

		Node sibling = findNode(siblingFullKey.split("\\."));
		if (sibling == null) {
			return false;
		}
		Node parent = sibling.getParent();
		int place = parent.getChildren().indexOf(sibling);
		if (addType == null || addType == AddSiblingType.backward) {
			place++;
		}
		addTo(parent, key, value, place);
		return true;
	}

	public Node findNode(String... keys) {
		Node n = null;
		boolean root = true;
		for (String key : keys) {
			if (root) {
				n = map.get(key);
				root = false;
			} else if (n != null) {
				n = findChildNode(n, key);
			}
		}
		return n;
	}

	public Node findChildNode(Node n, String childKey) {
		for (Node c : n.getChildren()) {
			if (childKey.equals(c.getKey())) {
				return c;
			}
		}
		return null;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	private void setCurrentNode(Node node) {
		this.currentNode = node;
	}

	private void assertNotNull(String key) {
		if (key == null) {
			throw new NodeException("node key can't be null or ''.");
		}
	}

	public LinkedHashMap<String, Node> getTreeMap() {
		return map;
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public Node get(String key) {
		return map.get(key);
	}

	/**
	 * 添加兄弟节点的方向.
	 * 
	 * @author myace
	 * 
	 */
	public static enum AddSiblingType {
		/**
		 * 向后添加.
		 */
		backward,
		/**
		 * 向前添加.
		 */
		forward;

	}

}
