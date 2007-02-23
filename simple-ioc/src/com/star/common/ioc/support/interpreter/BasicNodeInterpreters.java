package com.star.common.ioc.support.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.ioc.support.NodeInterpreter;
import com.star.common.util.ClassUtils;
import com.star.common.util.Node;

/**
 * һЩ�򵥵�NodeInterpreterʵ�֡�
 * 
 * @see NodeInterpreter
 * @author liuwei
 * @version 1.0
 */
public class BasicNodeInterpreters {

	// ===========================================================

	/**
	 * int�������磺��a=(int)3������ʾ��a=3;�����ߡ�a=new Integer(3);��
	 */
	public static class IntNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Integer.valueOf(valueString);
		}
	}

	/**
	 * String�������磺��a=(string)3������ʾ��a="3";��
	 */
	public static class StringNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return valueString;
		}
	}

	/**
	 * String������Trim���磺��a=(string) 3 ������ʾ��a="3";��
	 */
	public static class TrimNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return valueString == null ? null : valueString.trim();
		}
	}

	/**
	 * boolean�������磺��a=(boolean)true������ʾ��a=true;�����ߡ�a=Boolean.TRUE;��
	 */
	public static class BooleanNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Boolean.valueOf(valueString);
		}
	}

	/**
	 * long�������磺��a=(long)3������ʾ��a=3L;�����ߡ�a=new Long(3);��
	 */
	public static class LongNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Long.valueOf(valueString);
		}
	}

	/**
	 * float�������磺��a=(float)3������ʾ��a=3F;�����ߡ�a=new Float(3);��
	 */
	public static class FloatNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return Float.valueOf(valueString);
		}
	}

	/**
	 * class�������磺��a=(class)com.star.A������ʾ��a=com.star.A.class;��
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
	 * null�������磺��a=(null)������ʾ��a=null;��
	 */
	public static class NullNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return null;
		}
	}

	/**
	 * Node�������磺��a=(node)������ʾֱ�ӷ���a��Ӧ��Node����
	 */
	public static class NodeNodeInterpreter extends AbstractNodeInterpreter {
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) {
			return node;
		}
	}

	/**
	 * �������á� �磺��b=(ref)a�� ����ʾb���ö���a�����aΪFactoryBean����������a�����Ķ���
	 */
	public static class RefNodeInterpreter implements NodeInterpreter {
		public Object createObject(Node node, String valueString,
				String interpreterName, Map<Object, Object> context,
				BeanFactory beanFactory) {
			return beanFactory.getBean(valueString.trim(), context);
		}
	}

	/**
	 * �ǵ����ڵ�������� ���󴴽������Node�л���Ľ���� �磺��a=(no-singleton)com.star.A��
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
