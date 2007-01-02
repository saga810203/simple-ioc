package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;

public class StackLayoutFactory extends ParameterCompositeFactory{
	public StackLayoutFactory(){}
	public StackLayoutFactory(String id,String parameters, IControlFactory... children) {
		super(id,parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		StackLayout layout = new StackLayout();
		layout.marginWidth = getIntParam("marginWidth",layout.marginWidth);
		layout.marginHeight = getIntParam("marginHeight",layout.marginHeight);
		
		Composite c = new Composite(parent, SWT.NONE);
		c.setLayout(layout);
		return c;
	}
	
	@Override
	protected void createChildren(Composite control) {
		super.createChildren(control);
		if(control.getChildren().length>0)
			((StackLayout)control.getLayout()).topControl=control.getChildren()[0];
		control.layout();
	}
	
	
	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { "marginWidth", "marginHeight"};
	}
}
