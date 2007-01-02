package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.support.ParameterCompositeFactory;

public class FButtonBar extends ParameterCompositeFactory {
	public FButtonBar(){}
	public FButtonBar(String id, String parameters, IControlFactory... children) {
		super(id, parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		
		Composite c = new Composite(parent,SWT.NONE);
		GridLayout g = new GridLayout();
		g.numColumns = 1;	
		c.setLayout(g);

		Composite buttonComp = new Composite(c, SWT.NONE);
		GridData data = new GridData( GridData.FILL_HORIZONTAL);
		data.horizontalAlignment=GridData.END;
		buttonComp.setLayoutData(data);
		
		RowLayout layout = new RowLayout();
		layout.spacing=15;
		layout.pack=false;
		buttonComp.setLayout(layout);
		
		createChildren2(buttonComp);
		
		return c;
	}

	protected void createChildren2(Composite control) {
		IControlFactory[] children = getChildren();
		for(int i=0;i<children.length;i++){
			createChild(control, i);
		}
	}
	
	@Override
	protected void createChildren(Composite control) {

	}
	
	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { };
	}

}
