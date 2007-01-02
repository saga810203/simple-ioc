package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.CompositeFactorys;
import com.star.common.util.Node;

public class TemplateLayoutNodeInterpreter extends
		AbstractControlFactoryNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		IControlFactory template = beanFactory.getBean(valueString,
				IControlFactory.class).clone();
		int n = node.getChildren().size();
		for (int i = 0; i < n; i++) {
			Node c = node.getChildren().get(i);

			int index = c.getKey().indexOf('.');
			if (index > 0) {
				String cfId = c.getKey().substring(0, index);
				String attrKey = c.getKey().substring(index + 1);
				Object attr = beanFactory.getBean(c.getValue());
				CompositeFactorys.setAttribute(template, cfId, attrKey, attr);
			} else {
				CompositeFactorys.replacePlaceholder(template, c.getKey(),
						beanFactory
								.getBean(c.getValue(), IControlFactory.class));
			}
		}

		return template;
	}
}
