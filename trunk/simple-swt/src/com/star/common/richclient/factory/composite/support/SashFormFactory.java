package com.star.common.richclient.factory.composite.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.factory.IControlFactory;

public class SashFormFactory extends ParameterCompositeFactory {
	public SashFormFactory(){}
	public SashFormFactory(String id,String parameters, IControlFactory... children) {
		super(id, parameters, children);
	}

	@Override
	protected Composite doCreateComposite(Composite parent) {
		SashForm c = new SashForm(parent, SWT.HORIZONTAL);
		int orientation="v".equals(getStrParam("type"))?SWT.VERTICAL:SWT.HORIZONTAL;
		c.setOrientation(orientation);

		if(containsParamKey("background")){
			c.setBackground((Color)getParam("background"));
		}
		if(containsParamKey("foreground")){
			c.setForeground((Color)getParam("foreground"));
		}
		
		return c;
	}
	
	protected void createChildren(Composite control) {
		super.createChildren(control);
		SashForm c = (SashForm)control;
		if(containsParamKey("weight0") && containsParamKey("weight1") ){
			c.setWeights(new int[]{getIntParam("weight0"),getIntParam("weight1")});
		}
	}
	
	@Override
	protected Control createChild(Composite control, int i) {
		Control c = super.createChild(control, i);
		if(containsParamKey("maximizedControl")){
			int index = getIntParam("maximizedControl");
			if(index == i){
				((SashForm)control).setMaximizedControl(c);
			}
		}	
		return c;
	}
	
	@Override
	protected Object[] createLayoutDatas(int n) {
		checkChildrenNumber(2, n);
		return null;
	}
	
	@Override
	protected String[] doGetValidParamKey() {
		return new String[] { "type", "weight0", "weight1", "maximizedControl", "background", "foreground" };
	}
	
}
