package com.star.common.ioc.support.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.ioc.support.NodeInterpreter;
import com.star.common.util.ClassUtils;
import com.star.common.util.Node;

/**
 * 一些简单的NodeInterpreter实现。
 * 
 * @see NodeInterpreter
 * @author liuwei
 * @version 1.0
 */
public class BasicNodeInterpreters {

	// ===========================================================

	/**
	 * int解析。如：“a=(int)3”，表示“a=3;”或者“a=new Integer(3);”
	 */
	public static class IntNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Integer.valueOf(valueString);
		}
	}

	/**
	 * String解析。如：“a=(string)3”，表示“a="3";”
	 */
	public static class StringNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return valueString;
		}
	}

	/**
	 * String解析并Trim。如：“a=(string) 3 ”，表示“a="3";”
	 */
	public static class TrimNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return valueString == null ? null : valueString.trim();
		}
	}

	/**
	 * boolean解析。如：“a=(boolean)true”，表示“a=true;”或者“a=Boolean.TRUE;”
	 */
	public static class BooleanNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Boolean.valueOf(valueString);
		}
	}

	/**
	 * long解析。如：“a=(long)3”，表示“a=3L;”或者“a=new Long(3);”
	 */
	public static class LongNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Long.valueOf(valueString);
		}
	}

	/**
	 * float解析。如：“a=(float)3”，表示“a=3F;”或者“a=new Float(3);”
	 */
	public static class FloatNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Float.valueOf(valueString);
		}
	}

	/**
	 * class解析。如：“a=(class)com.star.A”，表示“a=com.star.A.class;”
	 */
	public static class ClassNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return getClassForName(valueString, beanFactory);
		}

		public static Class getClassForName(String valueString,
				BeanFactory beanFactory) {
			int i = valueString.indexOf("..");
			if (i >= 0) {
				String prefixBeanId = valueString.substring(0, i + 2);
				String prefix = beanFactory.getBean(prefixBeanId).toString();
				valueString = prefix
						+ valueString.substring(i + 2, valueString.length());
			}

			Class clazz = ClassUtils.getClassForName(valueString.trim());
			return clazz;
		}

	}

	/**
	 * null解析。如：“a=(null)”，表示“a=null;”
	 */
	public static class NullNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return null;
		}
	}

	/**
	 * Node解析。如：“a=(node)”，表示直接返回a对应的Node对象。
	 */
	public static class NodeNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return node;
		}
	}

	/**
	 * 对象引用。 如：“b=(ref)a” ，表示b引用对象a；如果a为FactoryBean对象，则引用a创建的对象。
	 */
	public static class RefNodeInterpreter implements NodeInterpreter {
		public Object createObject(Node node, String valueString,
				String interpreterName, Map<Object, Object> context,
				BeanFactory beanFactory) {
			return beanFactory.getBean(valueString.trim(), context);
		}
	}

	/**
	 * 非单例节点解析器。 对象创建后，清除Node中缓存的结果。 如：“a=(no-singleton)com.star.A”
	 */
	public static class NoSingletonNodeInterpreter implements NodeInterpreter {
		public Object createObject(Node node, String valueString,
				String interpreterName, Map<Object, Object> context,
				BeanFactory beanFactory) {
			Object result = beanFactory.getBean(node, valueString, context);
			DefaultBeanFactory dbf =((DefaultBeanFactory)beanFactory);
			dbf.removeCacheBean(node);
			return result;
		}
	}

}
