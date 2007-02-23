package com.star.common.ioc.support.interpreter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.Node;
import com.star.common.util.bean.MethodUtils;

/**
 * �������ýڵ�������� <br>
 * �ý�����������һ������ķ����������������Ϊ�ڵ㴦������
 * �磺
 * <pre> a 		= (method-invoke)createA
 * 			-object	= (ref)f
 * 			-java.lang.Integer	= (int)3
 * 			-	= (string)abc
 * b 		= (method-invoke)createB
 * 			-class	= com.star.C
 * 			-	= (int)3
 * 			-	= (string)abc
 * </pre>
 * �൱�ڣ�
 * <code>a=f.createA(3,"abc")</code> <br>
 * �� <code>b=com.star.C.createB(3,"abc")</code>
 * <br>���ڵ��õĲ���Node����Ϊ�������ͣ�ֵΪ������ֵ�������������Ϊ�գ���ȡ������class���棬��ʱ��������Ϊ�ա�
 * @author liuwei
 * @version 1.0
 */
public class MethodInvokeNodeInterpreter extends AbstractNodeInterpreter {
	
	public Object createObject(Node node, String valueString,
			String interpreterName, Map<Object, Object> context, BeanFactory beanFactory) {
		Object result =super.createObject(node, valueString,
				 interpreterName,  context, beanFactory);
		DefaultBeanFactory dbf =((DefaultBeanFactory)beanFactory);
		dbf.removeCacheBean(node);
		return result;
	}
	
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Object o = null;
		Class clazz = null;
		List<Object> args = new ArrayList<Object>();
		List<Class> argClasses = new ArrayList<Class>();
		for (Node n : node.getChildren()) {
			if ("object".equals(n.getKey())) {
				o = beanFactory.getBean(n,context);
			} else if ("class".equals(n.getKey())) {
				clazz = BasicNodeInterpreters.ClassNodeInterpreter.getClassForName(n.getValue(),beanFactory);
			} else {
				String className = n.getKey();
				Class argClass = null;
				Object arg = beanFactory.getBean(n, n.getValue(), null);
				if (className != null && !"".equals(className)) {
					argClass = BasicNodeInterpreters.ClassNodeInterpreter.getClassForName(className,beanFactory);
				} else {
					argClass = arg.getClass();
				}
				args.add(arg);
				argClasses.add(argClass);
			}
		}

		Class[] classes = new Class[argClasses.size()];
		argClasses.toArray(classes);
		Object result = null;
		if (o != null) {
			result = MethodUtils.invokeMethod(o, valueString, args.toArray(), classes);
		} else {
			Method m = MethodUtils.getAccessibleMethod(clazz, valueString,
					classes);
			result = m.invoke(null, args.toArray());
		}
		return result;
	}
}