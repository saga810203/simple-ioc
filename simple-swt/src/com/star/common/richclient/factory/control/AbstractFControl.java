package com.star.common.richclient.factory.control;

import org.eclipse.swt.SWT;

public abstract class AbstractFControl extends LabelElementControlFactory {
	
	private int style =SWT.NONE;
	
	public AbstractFControl() {
	}

	public AbstractFControl(int style) {
		this.style=style;
	}
	
	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}
	

	
}