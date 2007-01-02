package com.star.sms.richclient.support;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.factory.ControlCreateListener;
import com.star.common.richclient.factory.IControlFactory;

public class ControlFactoryWrapper implements IControlFactory{
	
	private IControlFactory delegate;
	
	public final void setDelegate(IControlFactory delegate) {
		this.delegate = delegate;
		doHandle(delegate);
	}
	
	public void doHandle(IControlFactory controlFactory) {}


//======================= delegate =========================
	
	public final void addCreateListener(ControlCreateListener listener) {
		delegate.addCreateListener(listener);
	}

	public final IControlFactory clone() {
		return delegate.clone();
	}

	public final Control createControl(Composite parent) {
		return delegate.createControl(parent);
	}

	public final Object getAttribute(String key) {
		return delegate.getAttribute(key);
	}

	public String getId() {
		return delegate.getId();
	}

	public final void removeAllCreateListener() {
		delegate.removeAllCreateListener();
	}

	public final void removeCreateListener(ControlCreateListener listener) {
		delegate.removeCreateListener(listener);
	}

	public final void setAttribute(String key, Object attribute) {
		delegate.setAttribute(key, attribute);
	}

	public void setId(String id) {
		delegate.setId(id);
	}



}
