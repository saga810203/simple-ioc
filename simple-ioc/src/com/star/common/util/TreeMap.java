package com.star.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

/**
 * �����д����ռ����� ��properties��ʽ�ļ�����Ϊ��Node���� <br>
 * �ļ���ʽ���£�
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
 * ��properties��ʽ��һ�£�keyǰ��Ŀո񡢡�:�����ߡ�=�����ߵĿո��Լ�value����Ŀո񶼽������ԡ� <br>
 * һ��key-value��������һ��Node���󡣲���key��value ������ʡ�� �硰-key01 :������key1���� ��key��ʡ��ʱ���硰-- :
 * value��������ʡ��ʱ���硰--���� <br>
 * ����ʱ��������ÿ��keyǰ��ġ�-������Ŀ������Node�����еĲ�Ρ� <br>
 * ������Ϻ��ڽ��Map�б���Ķ���Ϊ���Ϊ0��Node�����key-Nodeӳ�䡣
 * <p>
 * ����ʹ�á�<-�������ע��ڵ㣬��<-���ĺ�һλ���Ϊ�������ʾ�����λ�ã����Ϊ��-�����ʾ���뵽������磺 <br>
 * 
 * <pre>
 *   key0                        :	value0
 *           -key00              :	value00
 *   key0&lt;-0key01                :	value01
 *                   --key010    :	value010
 *   key0&lt;--key02                :	value02
 * </pre>
 * 
 * ��key0<-0key01������Node{key0}���ӽڵ��б�ĵ�һλ����һ��{key:"key01", value:"value01"}�Ľڵ㣬
 * ���Ҹ��ݡ�--key010������֪�����ýڵ��»����ӽڵ㡣
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
	 * ���������ļ��� �ļ�·�����磺��com/star/common/util/a.properties�� ����
	 * ��file:c:/a.properties����
	 * 
	 * @param classLoader
	 *            �����ļ�ʱʹ�õ����������
	 * @param filePaths
	 *            �ļ�·��
	 * @return this������
	 */
	public synchronized Map<String, Node> load(Class location,
			String... filePaths) {
		for (String filePath : filePaths) {
			load(location, filePath);
		}
		return this;
	}

	/**
	 * ͨ�����������ء�
	 * 
	 * @param inStream
	 *            ������
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
	 * ����һ�������Ľڵ�������һ���½ڵ㡣
	 * 
	 * @param node
	 *            ������Ľڵ�
	 * @return �½ڵ�
	 */
	public synchronized Node add(Node node) {
		return put(node.getKey(), node);
	}

	/**
	 * ����һ�������Ľڵ�������һ���½ڵ㡣
	 * 
	 * @param node
	 *            ������Ľڵ�
	 * @return �½ڵ�
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
				// �ڵ�ע��
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
	 * �ڵ�ע�롣 a.b<--c ��c�ڵ�ע�뵽a�ڵ�.b�ڵ��¡�
	 * 
	 * @param key
	 * @param value
	 * @param index "
	 *            <-"��key�е�λ��
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