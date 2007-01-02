package com.star.common.richclient.interpreter;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.richclient.factory.ControlFactoryFactory;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.control.FLabel;
import com.star.common.richclient.layout.GridLayoutBuilder;
import com.star.common.richclient.layout.GridLayoutTemplate;
import com.star.common.util.Node;
import com.star.common.util.StringUtils;

public class FormNodeInterpreter extends AbstractControlFactoryNodeInterpreter {

	private Map<String, Class> controlTypeMap;

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {

		String formId = node.getKey();
		boolean haveLayoutTemplate = valueString.length() != 0;
		GridLayoutTemplate layoutTemplate = null;
		if (haveLayoutTemplate) {
			layoutTemplate = beanFactory.getBean(valueString,
					GridLayoutTemplate.class);
		}

		GridLayoutBuilder builder = new GridLayoutBuilder(formId);

		for (Node c : node.getChildren()) {
			// name(text) => [name,text]
			if ("(row)".equals(c.getKey())) {
				builder.row();
				continue;
			}

			String[] temp = StringUtils.splite2(c.getKey());
			String propertyName = temp[0];
			String controlType = temp[1];
			String label = getText(c.getValue(), propertyName);
			IControlFactory cf1 = new FLabel(label);
			if (haveLayoutTemplate) {
				layoutTemplate.add(builder, cf1);
			} else {
				builder.cell(cf1, "h=l:p:n");
			}
			IControlFactory cf2 = getControlFactory(formId,propertyName, controlType);
			if (haveLayoutTemplate) {
				layoutTemplate.add(builder, cf2);
			} else {
				add(builder, c, cf2);
			}
		}
		return builder;

	}

	private void add(GridLayoutBuilder builder, Node c, IControlFactory cf) {
		String layoutData = "h=f:p:g";
		if (c.getChildren().size() > 0) {
			layoutData = c.getChildren().get(0).getValue();
		}
		builder.cell(cf.getId(),cf, layoutData);
	}

	private IControlFactory getControlFactory(String formId,String propertyName,
			String controlType) throws InstantiationException,
			IllegalAccessException {
		IControlFactory cf = null;
		Class<?> controlClass = controlTypeMap
				.get(controlType == null ? "text" : controlType);
		if(controlClass.isAssignableFrom(ControlFactoryFactory.class)){
			cf = ((ControlFactoryFactory)controlClass.newInstance()).create();
		}
		else{
			cf = (IControlFactory)controlClass.newInstance();
		}
		
		// if ("text".equals(controlType)) {
		// cf = new FText();
		// } else if ("password".equals(controlType)) {
		// cf = new FText(SWT.PASSWORD | SWT.BORDER);
		// } else if ("combo".equals(controlType)) {
		// cf = new FCombo();
		// }
		// throw new RuntimeException("not can't create controlFactory for '"
		// + controlType + "'.");

		cf.setId(formId==null?propertyName:(formId+"."+propertyName));
		return cf;

	}

	public Map<String, Class> getControlTypeMap() {
		return controlTypeMap;
	}

	public void setControlTypeMap(Map<String, Class> controlTypeMap) {
		this.controlTypeMap = controlTypeMap;
	}

}
