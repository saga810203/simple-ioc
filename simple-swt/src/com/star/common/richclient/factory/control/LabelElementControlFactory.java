package com.star.common.richclient.factory.control;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.factory.ControlFactory;
import com.star.common.richclient.factory.LabelElement;

public class LabelElementControlFactory extends ControlFactory implements LabelElement{

	private String text;

	private Image image;

	private String toolTipText;
	
    public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToolTipText() {
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	@Override
	protected Control doCreateControl(Composite parent) {
		return null;
	}
}
