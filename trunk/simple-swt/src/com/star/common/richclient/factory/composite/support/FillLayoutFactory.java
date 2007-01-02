package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;

public class FillLayoutFactory extends ParameterCompositeFactory {
	public FillLayoutFactory(){}
	/**
	 * 构造体。
	 * 
	 * @param parameters
	 *            参数字符串
	 */
	public FillLayoutFactory(String id,String parameters,
			IControlFactory... children) {
		super(id,parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		FillLayout layout = new FillLayout();
		
		layout.type = "v".equals(getStrParam("type"))?SWT.VERTICAL:SWT.HORIZONTAL;
		
		layout.marginWidth = getIntParam("marginWidth", layout.marginWidth);
		layout.marginHeight = getIntParam("marginHeight", layout.marginHeight);
		
		layout.spacing = getIntParam("spacing",layout.spacing);
		
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(layout);
		return c;
	}

	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { "type",  "marginWidth", "marginHeight", "spacing" };
	}

}