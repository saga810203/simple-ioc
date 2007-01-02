package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.AbstractNodeInterpreter;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.support.MessageSource;
import com.star.common.util.Node;

public abstract class AbstractControlFactoryNodeInterpreter extends AbstractNodeInterpreter {

	private MessageSource messageSource;

	public Object createObject(Node node, String valueString,
			String interpreterName, Map<Object, Object> context,
			BeanFactory beanFactory) {
		IControlFactory c = (IControlFactory) super.createObject(node, valueString,
				interpreterName, context, beanFactory);
		//return c==null?null: c.clone();
		return c;
	}

	protected String getText(String text, String propertyName) {
		String name;
		if (text == null || text.length() == 0) {
			name = propertyName;
		} else if (text.charAt(0) == '%') {
			String nameCode = text.substring(1);
			name = messageSource.getMessage(nameCode, text);
		} else {
			name = text;
		}
		return name;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
