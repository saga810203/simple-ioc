package com.star.common.richclient.form;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.star.common.richclient.form.support.ComboValueControl;
import com.star.common.richclient.form.support.TextValueControl;

/**
 * @author myace
 * @version 1.0
 */
public class Register {

	private static Register instance = new Register();

	private Map<Class, IValueControl> valueControlMap = new HashMap<Class, IValueControl>();

	private Map<Class, Map<Class, IConvertor>> convertorMap = new HashMap<Class, Map<Class, IConvertor>>();

	public static Register get() {
		return instance;
	}

	public void init() {
		putValueControl(Text.class, new TextValueControl());
		putValueControl(Combo.class, new ComboValueControl());
	}

	public <T extends Control> void putValueControl(Class<T> controlClass,
			IValueControl<T> valueControl) {
		valueControlMap.put(controlClass, valueControl);
	}

	@SuppressWarnings("unchecked")
	public <T extends Control> IValueControl<T> getValueControl(Class<T> controlClass) {
		return valueControlMap.get(controlClass);
	}

	public void putConvertor(Class src, Class target, IConvertor convertor) {
		Map<Class, IConvertor> map = convertorMap.get(src);
		if (map == null) {
			map = new HashMap<Class, IConvertor>();
			convertorMap.put(src, map);
		}
		map.put(target, convertor);
	}
	
	public IConvertor getConvertor(Class src, Class target) {
		Map<Class, IConvertor> map = convertorMap.get(src);
		if (map != null) {
			return map.get(target);
		}
		return null;
	}


}
