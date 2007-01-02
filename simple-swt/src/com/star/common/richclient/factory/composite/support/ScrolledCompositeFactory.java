package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.factory.IControlFactory;

public class ScrolledCompositeFactory extends ParameterCompositeFactory {
	public ScrolledCompositeFactory(){}
	public ScrolledCompositeFactory(String id,String parameters,
			IControlFactory... children) {
		super(id,parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		int style = SWT.NONE;
		if(getBoolParam("BORDER",false)){
			style = style |SWT.BORDER ;
		}
		if(getBoolParam("H_SCROLL",true)){
			style = style |SWT.H_SCROLL ;
		}
		if(getBoolParam("V_SCROLL",true)){
			style = style |SWT.V_SCROLL ;
		}

		ScrolledComposite c = new ScrolledComposite(parent, style);
		
		if(containsParamKey("expandHorizontal")){
			c.setExpandHorizontal(getBoolParam("expandHorizontal"));
		}
		if(containsParamKey("expandVertical")){
			c.setExpandHorizontal(getBoolParam("expandVertical"));
		}
		if(containsParamKey("minHeight")){
			c.setMinHeight(getIntParam("minHeight"));
		}
		if(containsParamKey("minWidth")){
			c.setMinWidth(getIntParam("minWidth"));
		}
		if(containsParamKey("alwaysShowScrollBars")){
			c.setAlwaysShowScrollBars(getBoolParam("alwaysShowScrollBars"));
		}
		
		return c;
	}

	@Override
	protected Control createChild(Composite control, int i) {
		Control c = super.createChild(control, i);
		((ScrolledComposite) control).setContent(c);
		//c.setSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		c.pack();
		return c;
	}

	@Override
	protected Object[] createLayoutDatas(int n) {
		checkChildrenNumber(1, n);
		return null;
	}

	@Override
	protected String[] doGetValidParamKey() {
		return new String[] {"BORDER","H_SCROLL","V_SCROLL", "expandHorizontal", "expandVertical",
				"minHeight", "minWidth", "alwaysShowScrollBars" };
	}

}
