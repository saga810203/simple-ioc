package com.star.common.ioc.support.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.BeanFactoryAware;
import com.star.common.ioc.support.interpreter.BasicNodeInterpreters.ClassNodeInterpreter;
import com.star.common.util.Node;
import com.star.common.util.bean.MethodUtils;
import com.star.common.util.bean.PropertyUtils;

/**
 * bean构建的节点处理器。<br>
 * 实现了简单的对象创建功能（包括FactoryBean、init方法的支持）。<br>
 * 通过节点树的描述，创建一个对象。如：<br>
 * <pre>
 * -a  					=	com.star.A 
 * 	--b				=	com.star.B
 *	--id				=	(beanId)
 *	--(init_method)			=	init
 *	--(factory_method)		=	getBean
 *</pre>
 * 表示:<br>
 * aFactory = new com.star.A();<br>
 * aFactory.setB(new com.star.B());<br>
 * aFactory.setId("a");<br>
 * aFactory.init();<br>
 * a=aFactory.getBean();<br>
 * 
 * @author liuwei
 * @version 1.0
 */
public class BeanNodeInterpreter extends AbstractNodeInterpreter {

	public static final String BEAN_ID = "(beanId)";

	public static final String INIT_METHOD = "(init_method)";

	public static final String FACTORY_METHOD = "(factory_method)";

	private static final String FACTORY_SELF = "!factory_self";

	private List<String> cycDependChecker = new ArrayList<String>();

	public Object createObject(Node node, String valueString,
			String interpreterName, Map<Object, Object> context, BeanFactory beanFactory) {
		boolean factorySelf = false;
		if(context != null && context.containsKey(FACTORY_SELF)) {
			factorySelf = true;
			context.remove(FACTORY_SELF);
		}
		
		Object result = super.createObject(node, valueString, interpreterName,
				context, beanFactory);

		if (!factorySelf) {
			try {
				result = product(node, result);
			} catch (Exception e) {
				throw new RuntimeException("bean load error: " + node, e);
			}
		}
		return result;
	}

	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Class clazz = getClassName(node, valueString, beanFactory);
		String beanId = node.getKey();
		// 顶级bean，可能使用ref进行引用所以要进行循环依赖检测
		boolean needCheck = node.getLay() == 0;
		if (needCheck)
			cycDependCheck(beanId);
		try {
			if (needCheck)
				cycDependChecker.add(beanId);
			Object result = createInstance(clazz,node,beanFactory,context);
			inject(node, clazz, result, beanFactory,context);
			init(node, clazz, result);
			return result;
		} finally {
			if (needCheck)
				cycDependChecker.remove(beanId);
		}
	}

	protected Object createInstance(Class clazz, Node node, BeanFactory beanFactory, Map<Object, Object> context) throws Exception {
		Object result = clazz.newInstance();
		return result;
	}

	protected Class getClassName(Node node, String className,
			BeanFactory beanFactory) {
		//	cache class;
		if (node.containsDataKey(Node.OTHER_CACHE)) {
			return (Class) node.getData(Node.OTHER_CACHE);
		} else {
			Class clazz = ClassNodeInterpreter.getClassForName(className,beanFactory);
			node.putData(Node.OTHER_CACHE, clazz);
			return clazz;
		}
	}

	private void cycDependCheck(String beanId) {
		int index = cycDependChecker.indexOf(beanId);
		if (index >= 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("cyc depend: ");
			for (String b : cycDependChecker.subList(index, cycDependChecker
					.size())) {
				sb.append(b).append("->");
			}
			sb.append(beanId);
			throw new RuntimeException(sb.toString());
		}
	}

	private void inject(Node node, Class clazz, Object result,
			BeanFactory beanFactory, Map<Object, Object> context) throws Exception {

		if (result instanceof BeanFactoryAware) {
			((BeanFactoryAware) result).setBeanFactory(beanFactory);
		}

		for (Node child : node.getChildren()) {
			String propertyName = child.getKey();

			if (BEAN_ID.equals(child.getValue())) {
				PropertyUtils.setSimpleProperty(result, propertyName, node
						.getKeyPath());
			} else if (child.getKey().charAt(0) != '(') {
				Object propertyValue = beanFactory.getBean(child,context);
				PropertyUtils.setNestedProperty(result, propertyName,
						propertyValue);
			}
		}
	}

	private void init(Node node, Class clazz, Object result) throws Exception {
		Node child = node.findChildByKey(INIT_METHOD);
		if (child != null) {
			String methodName = child.getValue();
			MethodUtils.invokeMethod(result, methodName, new Object[0]);
		}
	}

	private Object product(Node node, Object result) throws Exception {
		Node child = node.findChildByKey(FACTORY_METHOD);

		if (child != null) {
			String methodName = child.getValue();
			result = MethodUtils
					.invokeMethod(result, methodName, new Object[0]);
		}
		return result;
	}

	// ===========================================================
	
	/**
	 * 对象引用。
	 * 如：“b=(&ref)a” ，不论a是否为FactoryBean对象，b都引用对象a。 
	 */
	public static class FactoryRefNodeInterpreter extends
			AbstractNodeInterpreter {

		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			if (context == null) {
				context = new HashMap<Object, Object>();
			}
			context.put(FACTORY_SELF, Boolean.TRUE);
			return beanFactory.getBean(valueString.trim(), context);
		}
	}
}
