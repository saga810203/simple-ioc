package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.star.common.richclient.factory.IControlFactory;

public class TabFolderFactory extends ParameterCompositeFactory {

	public TabFolderFactory() {
	}

	public TabFolderFactory(String id, String parameters,
			IControlFactory... children) {
		super(id, parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		TabFolder c = new TabFolder(parent, SWT.NONE);
		return c;
	}

	protected void createChildren(Composite control) {
		IControlFactory[] children = this.getChildren();

		for (int i = 0; i < children.length; i++) {
			TabItem tabItem = new TabItem((TabFolder) control, SWT.NONE);

			IControlFactory cf = getChildren()[i];

			Object text = cf.getAttribute("text");
			Object image = cf.getAttribute("image");
			Object toolTipText = cf.getAttribute("toolTipText");

			if (text instanceof String) {
				tabItem.setText((String)text);
			}
			if (image instanceof Image) {
				tabItem.setImage((Image)image);
			}
			if (toolTipText instanceof String) {
				tabItem.setToolTipText((String)toolTipText);
			}
			Control c = super.createChild(control, i);

			tabItem.setControl(c);
		}
	}

}
