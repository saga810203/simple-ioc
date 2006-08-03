package com.star.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

/**
 * 树型行处理收集器。 将properties格式文件解析为，Node树。 <br>
 * 文件格式如下：
 * <p>
 * 
 * <pre>
 *   key0				:	value0
 *   	-key00			:	value00
 *  		--key000	:	value000
 *  	-key01			:	
 *   key1			
 *   	-key10			:	value10
 * </pre>
 * 
 * <p>
 * 和properties格式向一致，key前面的空格、“:”或者“=”两边的空格、以及value后面的空格都将被忽略。 <br>
 * 一对key-value，将创建一个Node对象。并且key或value 都可以省略 如“-key01 :”、“key1”； 当key被省略时形如“-- :
 * value”；都被省略时形如“--”。 <br>
 * 解析时，根据在每个key前面的“-”的数目决定该Node在树中的层次。 <br>
 * 解析完毕后，在结果Map中保存的对象为层次为0的Node对象的key-Node映射。
 * <p>
 * 可以使用“<-”标记来注入节点，“<-”的后一位如果为数组则表示插入的位置，如果为“-”则表示插入到最后，例如： <br>
 * 
 * <pre>
 *   key0                        :	value0
 *           -key00              :	value00
 *   key0&lt;-0key01                :	value01
 *                   --key010    :	value010
 *   key0&lt;--key02                :	value02
 * </pre>
 * 
 * “key0<-0key01”将在Node{key0}的子节点列表的第一位插入一个{key:"key01", value:"value01"}的节点，
 * 并且根据“--key010”可以知道，该节点下还有子节点。
 * 
 * @see Node
 * @author liuwei
 * @version 1.0
 */
public class TreeMap extends LinkedHashMap<String, Node> {

	private static final long serialVersionUID = 1L;

	public static final String FILE_PREFIX = "file:";

	private static final char LAY_CHAR = '-';

	private Stack<Node> s = new Stack<Node>();

	private int lastLay;

	private Node lastNode;

	/**
	 * @see TreeMap#load(ClassLoader, String...)
	 */
	public synchronized Map<String, Node> load(String... filePaths) {
		return load(null, filePaths);
	}

	/**
	 * 加载配置文件。 文件路径形如：“com/star/common/util/a.properties” 或者
	 * “file:c:/a.properties”。
	 * 
	 * @param classLoader
	 *            加载文件时使用的类加载器。
	 * @param filePaths
	 *            文件路径
	 * @return this对象本身
	 */
	public synchronized Map<String, Node> load(Class location,
			String... filePaths) {
		for (String filePath : filePaths) {
			load(location, filePath);
		}
		return this;
	}

	/**
	 * 通过输入流加载。
	 * 
	 * @param inStream
	 *            输入流
	 */
	public void load(InputStream inStream) {
		try {
			new Properties() {
				private static final long serialVersionUID = 1L;

				public synchronized Node put(Object key, Object value) {
					String key2 = (String) key;
					String value2 = (String) value;

					int lay = 0;
					int i = -1;
					while (key2.length() > ++i && key2.charAt(i) == LAY_CHAR) {
						lay++;
					}
					key2 = key2.substring(i);

					add(key2, value2, lay);
					return null;
				}
			}.load(inStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void load(Class location, String filePath) {
		InputStream in = null;
		try {
			if (filePath.startsWith(FILE_PREFIX))
				in = new FileInputStream(filePath.substring(FILE_PREFIX
						.length()));
			else if (location != null)
				in = location.getResourceAsStream(filePath);
			else
				in = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(
								filePath.charAt(0) == '/' ? filePath
										.substring(1) : filePath);
			if(in==null){
				throw new RuntimeException("can't read file[" + filePath + "] from ClassLocation["+location+"]");
			}
			load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// nothing;
				}
		}
	}

	/**
	 * @see TreeMap#add(Node)
	 */
	public synchronized Node add(String key, String value, int lay) {
		Node node = new Node();
		node.setKey(key);
		node.setValue(value);
		node.setLay(lay);
		return put(key, node);
	}

	/**
	 * 在上一次最后处理的节点后面加入一个新节点。
	 * 
	 * @param node
	 *            被加入的节点
	 * @return 新节点
	 */
	public synchronized Node add(Node node) {
		return put(node.getKey(), node);
	}

	/**
	 * 在上一次最后处理的节点后面加入一个新节点。
	 * 
	 * @param node
	 *            被加入的节点
	 * @return 新节点
	 */
	public synchronized Node put(String key, Node node) {
		doLine(node);
		if (lastLay == 0) {
			if (containsKey(key)) {
				System.err.println("Node(key[" + key
						+ "]) has exist, old node is replaced.");
			}
			super.put(key, lastNode);
		}
		return lastNode;
	}

	private void doLine(Node node) {
		int lay = node.getLay();
		if (lay == 0) {
			int i = node.getKey().indexOf("<-");
			if (i > 0) {
				// 节点注入
				lastNode = nodeInject(node.getKey(), node.getValue(), i);
				lastLay = lastNode.getLay();
				return;
			}
		}

		if (lay == 0) {
			s.clear();
		} else if (lay == lastLay + 1) {
			lastNode.addChild(node);
		} else if (lay <= lastLay) {
			while (lay - 1 < lastLay--)
				s.pop();
			s.peek().addChild(node);
		} else {
			throw new IllegalArgumentException("lay error. lay=" + lay
					+ ",but lastLay=" + lastLay + ", node=" + node
					+ ", lastNode=" + lastNode);
		}
		s.push(node);
		lastLay = lay;
		lastNode = node;
	}

	/**
	 * 节点注入。 a.b<--c 将c节点注入到a节点.b节点下。
	 * 
	 * @param key
	 * @param value
	 * @param index "
	 *            <-"在key中的位置
	 * @return
	 */
	private Node nodeInject(String key, String value, int index) {
		String parentKeyPath = key.substring(0, index);
		String[] parentKeys = parentKeyPath.split("\\.");

		int place = -1;
		char placeChar = key.charAt(index + 2);
		if (placeChar >= '0' && placeChar <= '9') {
			place = placeChar - '0';
		}

		Node parent = null;
		for (int j = 0; j < parentKeys.length; j++) {
			if (j == 0) {
				parent = this.get(parentKeys[j]);
			} else {
				if (parent == null) {
					throw new IllegalArgumentException("can't find node: "
							+ parentKeys[j - 1] + ", for Node = {key:" + key
							+ ", value:" + value + "}.");
				}
				parent = parent.findChildByKey(parentKeys[j]);
			}
		}

		key = key.substring(index + 3, key.length());
		int lay = parentKeys.length;

		Node node = new Node();
		node.setKey(key);
		node.setValue(value);
		node.setLay(lay);
		if (place == -1) {
			parent.addChild(node);
		} else {
			parent.getChildren().add(place, node);
		}

		return node;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (String k : keySet()) {
			Node v = get(k);
			visit(0, v, b);
		}
		return b.toString();
	}

	private void visit(int lay, Node node, StringBuilder b) {
		b.append(node.getLay());
		b.append(" ");
		for (int i = 0; i < lay; i++) {
			b.append("\t");
		}
		for (int i = 0; i < lay; i++) {
			b.append('-');
		}
		b.append(node.getKey());
		b.append('=');
		b.append(node.getValue());
		b.append("\n");

		for (Node child : node.getChildren()) {
			visit(lay + 1, child, b);
		}
	}

}