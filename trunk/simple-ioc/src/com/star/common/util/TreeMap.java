package com.star.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.star.common.util.TreeBuilder.AddSiblingType;

/**
 * 树型行处理收集器。 将properties格式文件解析为，Node树。 <br>
 * 文件格式如下：
 * <p>
 * 
 * <pre>
 *               key0				:	value0
 *               	-key00			:	value00
 *              		--key000	:	value000
 *              	-key01			:	
 *               key1			
 *               	-key10			:	value10
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
 *               key0                        :	value0
 *                       -key00              :	value00
 *               key0&lt;-0key01                :	value01
 *                               --key010    :	value010
 *               key0&lt;--key02                :	value02
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
	
    private String encoding = null;
    
	private TreeBuilder builder = new TreeBuilder(this);

	private int currentLay=0;
	
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
			if (in == null) {
				throw new RuntimeException("can't read file[" + filePath
						+ "] from ClassLocation[" + location + "]");
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
	 * 通过输入流加载。
	 * 
	 * @param inStream
	 *            输入流
	 */
	public void load(InputStream inStream) {
		try {
			Properties temp = new Properties() {
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
					TreeMap.this.currentLay = lay;
					return null;
				}
			};
			
            if (this.encoding == null) {
                temp.load(inStream);
            }
            else {
                new DefaultPropertiesPersister().load(temp, new InputStreamReader(inStream,
                        encoding));
            }

			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 在上一次最后处理的节点后面加入一个新节点。
	 * 
	 * @return 新节点
	 */
	private Node add(String key, String value, int lay) {
		if (lay == 0) {
			int i = key.indexOf("<-");
			if (i > 0) {
				nodeInject(key, value, i);
				return builder.getCurrentNode();
			}

			int j = key.indexOf("<+");
			if (j > 0) {
				nodeSiblingInject(key, value, j);
				return builder.getCurrentNode();
			}
		}
		doAdd( key,  value,  lay);
		return builder.getCurrentNode();
	}

	private void doAdd(String key, String value, int lay) {
		if (lay == 0) {
			builder.addRoot(key, value);
		} else if (lay == currentLay + 1) {
			builder.addChild(key, value);
		} else if (lay <= currentLay) {
			builder.addToParent(key, value,currentLay-lay+1);
		} else {
			throw new NodeException("add '" + key + "' error, currentLay is "
					+ currentLay +", lay is "+lay);
		}
	}
	
	
	/**
	 * 节点注入。 a.b<--c 将c节点注入到a节点.b节点下。
	 * 
	 * @param i '
	 *            <-'在key中的位置
	 * @return
	 */
	private void nodeInject(String key, String value, int i) {
		int place = -1;
		char placeChar = key.charAt(i + 2);
		if (placeChar >= '0' && placeChar <= '9') {
			place = placeChar - '0';
		}
		String parentKeys = key.substring(0, i);
		if (placeChar == '?') {
			builder
					.addChildToAttempt(key.substring(i + 3), value, parentKeys,
							place);
		} else {
			builder.addChildTo(key.substring(i + 3), value, parentKeys, place);
		}
	}

	private void nodeSiblingInject(String key, String value, int i) {
		char placeChar = key.charAt(i + 2);
		String parentKeys = key.substring(0, i);
		if (placeChar == '+') {
			builder.addSiblingTo(key.substring(i + 3), value, parentKeys,
					AddSiblingType.backward);
		} else if (placeChar == '-') {
			builder.addSiblingTo(key.substring(i + 3), value, parentKeys,
					AddSiblingType.forward);
		} else if (placeChar == '?') {
			builder.addSiblingToAttempt(key.substring(i + 3), value,
					parentKeys, AddSiblingType.backward);
		} else if (placeChar == '^') {
			builder.addSiblingToAttempt(key.substring(i + 3), value,
					parentKeys, AddSiblingType.forward);
		}
	}


	public Node findChildNode(Node n, String childKey) {
		return builder.findChildNode(n, childKey);
	}

	public Node findNode(String... parentKeys) {
		return builder.findNode(parentKeys);
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
		b.append(lay);
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

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}