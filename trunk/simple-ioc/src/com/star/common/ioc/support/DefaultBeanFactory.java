package com.star.common.ioc.support;

import java.util.HashMap;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.ClassUtils;
import com.star.common.util.Node;
import com.star.common.util.StringUtils;

/**
 * Ĭ�ϵĶ��󹤳��� <br>
 * ͨ������ڵ��������NodeInterpreter����������չ�ܹ������Ľڵ㡣<br>
 * һ����Ҫע��@default�ڵ���������ý���������ָ��ĳ����������һ��ʹ��boot�����������ء�<br>
 * Node.value�����������"(...)"ǰ׺����DefaultBeanFactory�����Ȳ����Ƿ�����Ӧ��"@..."�ڵ��������
 * ���������ʹ�øýڵ����������ڵ㡣<br>
 * �ο���default-interpreter-config.properties���ļ�ע��ڵ��������
 * 
 * @see com.star.common.ioc.support.interpreter.*
 * @author liuwei
 * @version 1.1
 */
public class DefaultBeanFactory implements BeanFactory {

	private static final char INTERPRETER_KEY_PREFIX = '@';

	private static final String DEFAULT_INTERPRETER_NAME = "default";

	private static final String BOOT_INTERPRETER_NAME = "boot";
	
	private NodeInterpreter bootNodeInterpreter ;

	private Map<String, Node> configMap;
	
	private BeanFactory parent;
	
	public DefaultBeanFactory(Map<String, Node> configTreeMap) {
		this.configMap = configTreeMap;
		this.bootNodeInterpreter = new BootNodeInterpreter();
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.star.common.ioc.BeanFactory#getBean(java.lang.String)
	 */
	public Object getBean(String beanId) {
		return getBean(beanId, (Map<Object, Object>)null);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.star.common.ioc.BeanFactory#getBean(java.lang.String,
	 *      java.lang.Class)
	 */
	public <T> T getBean(String beanId, Class<T> requiredType) {
		return getBean(beanId, null, requiredType);
	}
	
	public <T> T getBean(String beanId, Map<Object, Object> context, Class<T> requiredType) {
		Object o = getBean(beanId,context);
		if (o == null || requiredType == null
				|| requiredType.isAssignableFrom(o.getClass())) {
			return (T) o;
		}
		throw new RuntimeException("bean(beanId=" + beanId
				+ ") isn't assignable from " + requiredType.getName());
	}
	
	/**
	 * �÷�����������beanId��һ���ַ���'('������Ϊ�������ǵ�beanId������һ����Ҫ�����valueString��
	 * ��ʱ������һ����ʱNode������value����beanId��������ýڵ㣬������������Ľ�����ء�
	 * <p>��ʱ����ڵ�ʱ����һ���ڵ��������Ҫһ�������Ļ�������ʱ���Ե��ø÷�����������һ����������Ϣ��
	 * ���Բμ�BeanNodeInterpreter��FactoryRefNodeInterpreter�е����ʹ�÷�����
	 * @param beanId
	 * @param context ������Map
	 * @return
	 */
	public Object getBean(String beanId, Map<Object, Object> context) {
		if (beanId == null) {
			throw new IllegalArgumentException("beanId can't be null: beanId="
					+ beanId);
		}
		Node node = null;
		if (beanId.charAt(0) == '(') {
			node = new Node();
			node.setValue(beanId);
		} else if (containsBean(beanId)) {
			node = getConfigMap().get(beanId);
		} else {
			if(parent!=null){
				return parent.getBean(beanId, context);
			}
			throw new IllegalArgumentException("bean[" + beanId
					+ "] haven't a define.");
		}
		return getBean(node, node.getValue(), context);
	}
	
	public Object getBean(Node node) {
		return getBean(node, null);
	}
	
	public Object getBean(Node node, Map<Object, Object> context) {
		return getBean(node, node.getValue(), context);
	}
	
	/**
	 * �õ�node��Ӧ�Ķ���ͨ������valueString�����Եݹ�Ĵ���node.value�а�����ǰ׺��(...)����
	 * <br>�磺node.value="(a)(b)(c)text"������node����a�ڵ����������
	 * ��ʱa�ڵ�����������Ե��ñ���������������ʱ��valueString����ʱӦΪ"(b)(c)text"�����룬
	 * �Ϳ���ʹ��b�׶ν���������ڵ㡣
	 * @param node ������Ľڵ�
	 * @param valueString �����value�ַ���
	 * @param context
	 * @return
	 */
	public Object getBean(Node node, String valueString,
			Map<Object, Object> context) {
		String[] ics = StringUtils.split1(valueString);
		String valueString2 = ics[0];
		String interpreterName = ics[1];

		return interprete(node, valueString2, interpreterName, context);
	}

	private Object interprete(Node node, String valueString, String interpreterName,
			Map<Object, Object> context) {
		if(interpreterName == null){
			interpreterName = DEFAULT_INTERPRETER_NAME;
		}
		NodeInterpreter interpreter = null;
		if(BOOT_INTERPRETER_NAME.equals(interpreterName)){
			interpreter = bootNodeInterpreter;
		}
		else {
			interpreter = getBean(INTERPRETER_KEY_PREFIX + interpreterName ,
						NodeInterpreter.class);
		}
		return interpreter.createObject(node, valueString, interpreterName, context, this);
	}

	// ===========================================

	/**
	 * (non-Javadoc)
	 * @see com.star.common.ioc.BeanFactory#containsBean(java.lang.String)
	 */
	public boolean containsBean(String beanId) {
		return getConfigMap().get(beanId) != null;
	}

	protected Map<String, Node> getConfigMap() {
		return configMap;
	}

	public BeanFactory getParent() {
		return parent;
	}

	public void setParent(BeanFactory parent) {
		this.parent = parent;
	}
		
	private class BootNodeInterpreter implements NodeInterpreter{
		
		private Map<String,Object> cache = new HashMap<String,Object>();
		
		public Object createObject(Node node, String valueString, String interpreterName, Map<Object, Object> context, BeanFactory beanFactory) {
			try {
				Object r = cache.get(node.getKey());
				if(r==null){
					r = ClassUtils.getClassForName(valueString).newInstance();
					cache.put(node.getKey(),r);
				}
				return r;
			} catch (Exception e) {
				throw new RuntimeException("boot error.",e);
			}
		}
	}
}