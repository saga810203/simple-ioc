package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.NodeInterpreter;
import com.star.common.util.Node;
import com.star.common.util.bean.MethodUtils;

public class CloneNodeInterpreter implements NodeInterpreter {

	public Object createObject(Node node, String valueString,
			String interpreterName, Map<Object, Object> context,
			BeanFactory beanFactory) {
		Object o = beanFactory.getBean(valueString);
		return MethodUtils.invokeMethod(o,"clone",new Object[]{});
	}

}
