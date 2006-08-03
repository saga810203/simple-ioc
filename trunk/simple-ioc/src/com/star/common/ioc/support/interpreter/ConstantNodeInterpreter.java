package com.star.common.ioc.support.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.BasicNodeInterpreters.ClassNodeInterpreter;
import com.star.common.util.Node;

/**
 * 常量解析。
 * 如： “a = (constant)com.star.A.CONSTANT_1”，
 * 相当如 <code>a = com.star.A.CONSTANT_1; </code>
 * @author liuwei
 * @see 1.0
 */
public class ConstantNodeInterpreter extends AbstractNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {

		int i = valueString.lastIndexOf('.');

		String className = valueString.substring(0, i);
		String constantName = valueString
				.substring(i + 1, valueString.length());

		Class clazz = ClassNodeInterpreter.getClassForName(className,
				beanFactory);

		return clazz.getField(constantName).get(null);
	}

}
