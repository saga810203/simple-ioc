package com.star.common.richclient.factory;

import org.eclipse.swt.widgets.Control;

public class ControlCreateAdapter implements ControlCreateListener {
	private String controlFactoryIdByListen;
	
	public ControlCreateAdapter() {
	}
	
	public ControlCreateAdapter(String controlFactoryIdByListen) {
		this.controlFactoryIdByListen = controlFactoryIdByListen;
	}

	public final void preCreate(IControlFactory factory) {
		if(filte(factory)){
			doPreCreate(factory);
		}
	}

	protected void doPreCreate(IControlFactory factory) {
		
	}
	
	public final void postCreate(IControlFactory factory, Control control) {
		if(filte(factory)){
			doPostCreate(factory,control);
		}
		//factory.removeCreateListener(this);
	}

	protected void doPostCreate(IControlFactory factory, Control control) {
		
	}
	
	public boolean filte(IControlFactory factory) {
		if (controlFactoryIdByListen == null
				|| controlFactoryIdByListen.equals(factory.getId())) {
			return true;
		}
		return false;
	}
	
	public void addTo(IControlFactory cf){
		cf.addCreateListener(this);
	}
}
