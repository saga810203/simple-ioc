package com.star.common.richclient.component;

import java.util.Map;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.star.common.util.bean.PropertyUtils;

public class Form {

	private Map<String, Control> controlMap;

	private String formId;

	public void setValueToControl(Object bean) {
		for (String id : controlMap.keySet()) {
			if (id == null) {
				continue;
			}
			Control c = controlMap.get(id);
			if (formId != null && id.startsWith(formId + ".")) {
				id = id.substring(formId.length() + 1);
			}
			if (PropertyUtils.isReadable(bean, id)) {
				Object value = PropertyUtils.getProperty(bean, id);
				setValue(id, c, value);
			}
		}
	}

	public void setValueToObject(Object bean) {
		for (String id : controlMap.keySet()) {
			if (id == null) {
				continue;
			}
			Control c = controlMap.get(id);
			if (formId != null && id.startsWith(formId + ".")) {
				id = id.substring(formId.length() + 1);
			}
			if (PropertyUtils.isWriteable(bean, id)) {
				Class propertyType = PropertyUtils.getPropertyType(bean, id);
				PropertyUtils.setProperty(bean, id, getValue(id, c,
						propertyType));
			}
		}
	}

	private void setValue(String id, Control c, Object value) {
		if (c instanceof Text) {
			String v = (String) convert(value, String.class);
			((Text) c).setText(v == null ? "" : v);
		} else {
			// TODO
		}
	}

	private Object getValue(String id, Control c, Class propertyType) {
		Object sourceValue = null;
		if (c instanceof Text) {
			sourceValue = ((Text) c).getText();
		} else {
			// TODO
		}

		return convert(sourceValue, propertyType);
	}

	private Object convert(Object sourceValue, Class propertyType) {
		if (sourceValue == null) {
			return sourceValue;
		}
		if (propertyType.isAssignableFrom(sourceValue.getClass())) {
			return sourceValue;
		}
		// TODO
		return null;
	}

	public Map<String, Control> getControlMap() {
		return controlMap;
	}

	public void setControlMap(Map<String, Control> controlMap) {
		this.controlMap = controlMap;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

}
