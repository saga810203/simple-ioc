package com.star.common.richclient.form;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.Constant;
import com.star.common.util.bean.PropertyUtils;

/**
 * @author myace
 * @version 1.0
 */
public class FormHelp {

	private static final String CONTROL_VALUE_CLASS_KEY = "controlValueClass";

	private static final String PROPERTY_CLASS_KEY = "propertyClass";

	private Map<String, Control> map;

	public FormHelp(Composite c) {
		this.map = new HashMap<String, Control>();
		findPropertyControl(c);
	}

	/**
	 * 将对象属性值设置到控件中。
	 * 
	 * @param input
	 *            对象
	 * @throws ConvertRuntimeException
	 */
	public void setControlValues(Object input) throws ConvertRuntimeException{
		for (String property : map.keySet()) {
			Control control = map.get(property);
			Object propertyValue = getValueFromInput(input, property);
			setValueToControl(control, propertyValue);
		}
	}


	/**
	 * 将控件中的值设置到对象。
	 * 
	 * @param input
	 *            对象
	 * @throws ConvertRuntimeException
	 */
	public void setObjectProperties(Object input) throws ConvertRuntimeException {
		for (String property : map.keySet()) {
			Control control = map.get(property);
			Class targetClass = getPropertyClass(input, property, control);
			Object newPropertyValue = getValueFromControl(control, targetClass);
			setValueToInput(input, property, newPropertyValue);
		}
	}

	private Class getPropertyClass(Object input, String property,
			Control control) {
		Class targetClass = (Class) control.getData(PROPERTY_CLASS_KEY);
		if (targetClass == null && input != null) {
			targetClass = PropertyUtils.getPropertyType(input, property);
			control.setData(PROPERTY_CLASS_KEY, targetClass);
		}
		return targetClass;
	}

	public boolean isChanged(Object input) {
		for (String property : map.keySet()) {
			Control control = map.get(property);
			Object oldPropertyValue = getValueFromInput(input, property);
			Class targetClass = getPropertyClass(input, property, control);
			Object newPropertyValue;
			try {
				newPropertyValue = getValueFromControl(control, targetClass);
			} catch (ConvertRuntimeException e) {
				return false;
			}

			boolean equal = nullSafeEquals(oldPropertyValue, newPropertyValue);
			if (!equal) {
				return true;
			}
		}
		return false;
	}

	public String check() {
		for (String property : map.keySet()) {
			Control control = map.get(property);
			Class targetClass = (Class) control.getData(PROPERTY_CLASS_KEY);
			Object newPropertyValue = null;
			try {
				newPropertyValue = getValueFromControl(control, targetClass);
			} catch (ConvertRuntimeException e) {
				return e.getLocalizedMessage();
			}
			String msg = check(property, newPropertyValue, control);
			if (msg != null) {
				return msg;
			}
		}
		return null;
	}

	public String check(String property, Object newPropertyValue,
			Control control) {
		IValidator validator = getValidator(control);
		return validator.validate(property, newPropertyValue, control);
	}

	protected IValidator getValidator(Control control) {
		IValidator validator = (IValidator) control.getData("validator");
		return validator;
	}

	protected void setValueToInput(Object input, String property,
			Object newPropertyValue) {
		PropertyUtils.setProperty(input, property, newPropertyValue);
	}

	protected Object getValueFromInput(Object input, String property) {
		Object propertyValue = PropertyUtils.getProperty(input, property);
		return propertyValue;
	}

	protected void setValueToControl(Control c, Object propertyValue)
			throws ConvertRuntimeException {
		IValueControl valueControl = Register.get().getValueControl(
				c.getClass());
		if (propertyValue != null) {
			Class srcClass = propertyValue.getClass();
			Class targetClass = getControlValueClass(c, valueControl);
			if (targetClass != null && !srcClass.isAssignableFrom(targetClass)) {
				IConvertor convertor = Register.get().getConvertor(srcClass,
						targetClass);
				propertyValue = convertor.convert(propertyValue, targetClass);
			}
		}
		valueControl.setValue(c, propertyValue);
	}

	private Class getControlValueClass(Control c, IValueControl valueControl) {
		Class targetClass = (Class) c.getData(CONTROL_VALUE_CLASS_KEY);
		if (targetClass == null) {
			Object v = valueControl.getValue(c);
			if (v != null) {
				targetClass = v.getClass();
			}
		}
		return targetClass;
	}

	protected Object getValueFromControl(Control c, Class targetClass)
			throws ConvertRuntimeException {
		IValueControl valueControl = Register.get().getValueControl(
				c.getClass());
		Object value = valueControl.getValue(c);

		if (value != null) {
			Class srcClass = value.getClass();
			if (targetClass != null && !srcClass.isAssignableFrom(targetClass)) {
				IConvertor convertor = Register.get().getConvertor(srcClass,
						targetClass);
				value = convertor.convert(value, targetClass);
			}
		}

		return value;
	}

	public void modifyListener(final ContentModifyListener listener) {
		for (String property : map.keySet()) {
			Control control = map.get(property);
			addModifyListener(listener, control);
		}
	}
	
	protected void addModifyListener(final ContentModifyListener listener,
			Control c) {
		IValueControl valueControl = Register.get().getValueControl(
				c.getClass());
		valueControl.addModifyListener(listener, c);
	}

	private void findPropertyControl(Composite parent) {
		Control[] cs = parent.getChildren();
		for (int i = 0; i < cs.length; i++) {
			Control c = cs[i];
			String property = (String) c.getData(Constant.PROPERTY);
			if (property != null) {
				map.put(property, c);
			}
			if (c instanceof Composite) {
				findPropertyControl((Composite) c);
			}
		}
	}
	
	protected boolean nullSafeEquals(Object oldPropertyValue,
			Object newPropertyValue) {
		return oldPropertyValue == newPropertyValue
				|| (oldPropertyValue != null && oldPropertyValue
						.equals(newPropertyValue));
	}

	public static interface ContentModifyListener {
		void onModify(); // { firePropertyChange(PROP_DIRTY);}
	}
}
