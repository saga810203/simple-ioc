package com.star.common.richclient.factory.control.combo;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import com.star.common.richclient.factory.control.AbstractFControl;
import com.star.common.richclient.utils.list.PropertyLabelProvider;

public class FCombo extends AbstractFControl {

	private IBaseLabelProvider labelProvider;
	
	private IStructuredContentProvider contentProvider;
	
	private String renderPropertyName;

	public FCombo() {
	}

	public FCombo(int style) {
		super(style);
	}

	public Combo doCreateControl(Composite parent) {
		ComboViewer viewer = new ComboViewer(parent, getStyle());
		Combo combo = viewer.getCombo();
		combo.setData(viewer);
		
		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(getLabelProvider());

		return combo;
	}
	
	public IStructuredContentProvider getContentProvider() {
		if (contentProvider == null)
			contentProvider = new ArrayContentProvider();
		return contentProvider;
	}

	public void setContentProvider(IStructuredContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
	
	public IBaseLabelProvider getLabelProvider() {
		if(labelProvider == null){
			labelProvider =  new PropertyLabelProvider(getRenderPropertyName());
		}
		
		return labelProvider;
	}

	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public String getRenderPropertyName() {
		return renderPropertyName;
	}

	public void setRenderPropertyName(String propertyName) {
		this.renderPropertyName = propertyName;
	}

}
