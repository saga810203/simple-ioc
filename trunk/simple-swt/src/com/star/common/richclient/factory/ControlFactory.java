package com.star.common.richclient.factory;

import java.util.HashMap;
import java.util.Map;


import org.eclipse.core.runtime.ListenerList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * 抽象的控件工厂。
 * 
 * @author liuwei
 */
public abstract class ControlFactory implements IControlFactory {

	private String id;

	private Map<String, Object> attributes;

	protected ListenerList createListeners = new ListenerList();

	public ControlFactory() {
	}

	public ControlFactory(String id) {
		this.id = id;
	}

	public Control createControl(Composite parent) {
		fireCreateListener(true, null);
		Control control = doCreateControl(parent);
		fireCreateListener(false, control);
		return control;
	}

	protected abstract Control doCreateControl(Composite parent);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttribute(String key, Object attribute) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, attribute);
	}

	public Object getAttribute(String key) {
		if (attributes == null) {
			return null;
		}
		return attributes.get(key);
	}

	public void addCreateListener(ControlCreateListener listener) {
		createListeners.add(listener);
	}

	public void removeCreateListener(ControlCreateListener listener) {
		createListeners.remove(listener);
	}

	public void removeAllCreateListener() {
		createListeners.clear();
	}

	protected void fireCreateListener(boolean pre, Control control) {
		fireCreateListener(pre, this, control);
	}

	protected void fireCreateListener(boolean pre, IControlFactory cf,
			Control control) {
		Object[] listeners = createListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ControlCreateListener l = (ControlCreateListener) listeners[i];
			if (pre) {
				l.preCreate(cf);
			} else {
				l.postCreate(cf, control);
			}
		}
	}

	public ControlFactory clone() {
		ControlFactory c = null;
		try {
			c = (ControlFactory) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		Object[] listeners = this.createListeners.getListeners();
		int n = listeners.length;
		c.createListeners = new ListenerList(n >= 1 ? n : 1);
		for (int i = 0; i < n; i++) {
			Object t = listeners[i];
			c.createListeners.add(t);
		}
		if (this.attributes != null) {
			c.attributes = new HashMap<String, Object>();
			c.attributes.putAll(this.attributes);
		}
		return c;
	}

}
