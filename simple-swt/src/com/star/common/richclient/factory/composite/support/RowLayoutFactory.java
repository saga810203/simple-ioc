package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;

public class RowLayoutFactory extends ParameterCompositeFactory {
	public RowLayoutFactory(){}
	public RowLayoutFactory(String id, String parameters, IControlFactory... children) {
		super(id, parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		RowLayout layout = new RowLayout();

		layout.type = "v".equals(getStrParam("type"))?SWT.VERTICAL:SWT.HORIZONTAL;
		
		layout.wrap = getBoolParam("wrap",layout.wrap);
		layout.pack = getBoolParam("pack",layout.pack);
		layout.justify = getBoolParam("justify",layout.justify);

		layout.marginLeft = getIntParam("marginLeft",layout.marginLeft);
		layout.marginTop = getIntParam("marginTop",layout.marginTop);
		layout.marginRight = getIntParam("marginRight",layout.marginRight);
		layout.marginBottom = getIntParam("marginBottom",layout.marginBottom);
		layout.marginWidth = getIntParam("marginWidth",layout.marginWidth);
		layout.marginHeight = getIntParam("marginHeight",layout.marginHeight);

		layout.spacing = getIntParam("spacing",layout.spacing);
		
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(layout);
		return c;
	}

	@Override
	protected Object[] createLayoutDatas(int n) {
		// RowData[] ds = new RowData[n];
		// ds[0]=new RowData(100,30);
		return null;
	}

	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { "type", "wrap", "pack", "justify", "marginLeft",
				"marginTop", "marginRight", "marginBottom", "marginWidth", "marginHeight", "spacing" };
	}

}
