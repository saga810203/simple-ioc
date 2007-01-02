package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.AbstractNodeInterpreter;
import com.star.common.richclient.support.MessageSource;
import com.star.common.util.Node;

public class MessageNodeInterpreter extends AbstractNodeInterpreter {

	private boolean hasDefault = true;

	private MessageSource messageSource;


	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		String s = messageSource.getMessage(valueString);
		return (s == null && hasDefault) ? valueString : s;

	}

	public void setHasDefault(boolean hasDefault) {
		this.hasDefault = hasDefault;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
