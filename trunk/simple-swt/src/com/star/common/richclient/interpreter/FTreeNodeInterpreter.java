package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.BeanNodeInterpreter;
import com.star.common.richclient.factory.control.tree.FTree;
import com.star.common.util.Node;

public class FTreeNodeInterpreter extends BeanNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		FTree tree = (FTree) super.doCreateObject(node, valueString,
				beanFactory, context, interpreterName);
		tree.setId(node.getKey());
		return tree;
	}

	protected Class getClassName(Node node, String className,
			BeanFactory beanFactory) {
		return FTree.class;
	}

	protected Object createInstance(Class clazz, Node node,
			BeanFactory beanFactory, Map<Object, Object> context)
			throws Exception {

		String value = node.getValue();
		String valueString = value.substring(value.indexOf(')')+1);
		
		if (valueString.trim().length() != 0) {
			return (FTree)beanFactory.getBean(valueString.trim(), FTree.class).clone();
		} else {
			return new FTree();
		}
	}
	
}
