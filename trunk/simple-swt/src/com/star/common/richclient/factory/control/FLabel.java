package com.star.common.richclient.factory.control;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class FLabel extends AbstractFControl {
	
	public FLabel() {
	}

	public FLabel(int style) {
		super(style);
	}

	public FLabel(int style, String text) {
		this(style);
		setText(text);
	}

	public FLabel(String text) {
		setText(text);
	}
	
	public Label doCreateControl(Composite parent) {
		Label c = new Label(parent, getStyle());
		if (getText() != null) {
			c.setText(getText());
		}
		if (getImage() != null) {
			c.setImage(getImage());
		}
		if (getToolTipText() != null) {
			c.setToolTipText(getToolTipText());
		}
		return c;
	}

}