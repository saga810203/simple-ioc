package com.star.sms.richclient.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.ControlFactory;

public class ColorPane extends ControlFactory {

	protected int color = 0;

	public ColorPane() {
	}

	public ColorPane(String id) {
		super(id);
	}

	public ColorPane(String id, int color) {
		super(id);
		this.color = color;
	}

	public Composite doCreateControl(Composite parent) {
		Composite c1 = new Composite(parent, SWT.NONE);
		c1.setBackground(parent.getDisplay().getSystemColor(getColor()));
		return c1;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getColor() {
		return color == 0 ? SWT.COLOR_WHITE : color;
	}

}
