package com.star.common.richclient.factory.control;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class FButton extends AbstractFControl{

	public FButton() {
	}

	public FButton(int style) {
		super(style);
	}

	public FButton(int style, String text) {
		this(style);
		setText(text);
	}

	public FButton(String text) {
		setText(text);
	}
	
	public Button doCreateControl(Composite parent) {
		Button c = new Button(parent, getStyle());
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