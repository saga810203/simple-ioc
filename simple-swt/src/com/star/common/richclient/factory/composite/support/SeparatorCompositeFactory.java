package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.star.common.richclient.factory.IControlFactory;

public class SeparatorCompositeFactory extends ParameterCompositeFactory {

	public SeparatorCompositeFactory() {
	}

	public SeparatorCompositeFactory(String id, String parameters,
			IControlFactory... children) {
		super(id, parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		GridLayout layout = new GridLayout(2, false);

		layout.marginLeft = getIntParam("marginLeft", layout.marginLeft);
		layout.marginTop = getIntParam("marginTop", layout.marginTop);
		layout.marginRight = getIntParam("marginRight", layout.marginRight);
		layout.marginBottom = getIntParam("marginBottom", layout.marginBottom);
		layout.marginWidth = getIntParam("marginWidth", layout.marginWidth);
		layout.marginHeight = getIntParam("marginHeight", layout.marginHeight);

		layout.verticalSpacing = getIntParam("spacing", layout.verticalSpacing);

		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(layout);

		Label label = new Label(c, SWT.NONE);

		IControlFactory cf = this.getChildren()[0];
		
		Object text = cf.getAttribute("text");
		Object image = cf.getAttribute("image");
		Object toolTipText = cf.getAttribute("toolTipText");

		if (text instanceof String) {
			label.setText((String)text);
		}
		if (image instanceof Image) {
			label.setImage((Image)image);
		}
		if (toolTipText instanceof String) {
			label.setToolTipText((String)toolTipText);
		}
		
		Label sepLabel = new Label(c, SWT.SEPARATOR | SWT.HORIZONTAL);
		sepLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		return c;
	}

	@Override
	protected Object[] createLayoutDatas(int n) {
		checkChildrenNumber(1, n);
		GridData[] d = new GridData[2];
		d[1] = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		d[1].horizontalSpan = 2;
		return d;
	}

	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { "marginLeft", "marginTop", "marginRight",
				"marginBottom", "marginWidth", "marginHeight", "spacing" };
	}
}
