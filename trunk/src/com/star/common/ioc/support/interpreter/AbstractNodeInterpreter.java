package com.star.common.ioc.support.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.NodeInterpreter;
import com.star.common.util.Node;

/**
 * 抽象的节点解释器，带有结果缓存功能。
 * @author liuwei
 * @version 1.0
 */
public abstract class AbstractNodeInterpreter implements NodeInterpreter {
	
	public Object createObject(Node node, String valueString,
			String interpreterName, Map<Object, Object> context, BeanFactory beanFactory) {
		if (node.containsDataKey(Node.VALUE_CACHE)) {
			return node.getData(Node.VALUE_CACHE);
		} else {
			Object v = null;
			try {
				v = doCreateObject(node, valueString,
						beanFactory, context, interpreterName);
			} catch (Exception e) {
				throw new RuntimeException("bean load error: " + node, e);
			}
			node.putData(Node.VALUE_CACHE, v);
			return v;
		}
	}

	protected abstract Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception;

}