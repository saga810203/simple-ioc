package com.star.common.richclient.factory.control;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.factory.ControlFactory;

public class FNull extends ControlFactory{
	
	
	public FNull() {
		super();
		
	}

	public FNull(String id) {
		super(id);
		
	}

	protected Control doCreateControl(Composite parent){
		return new Composite(parent,SWT.NONE);
	}
}
