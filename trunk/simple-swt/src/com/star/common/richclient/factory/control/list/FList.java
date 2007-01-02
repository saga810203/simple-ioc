package com.star.common.richclient.factory.control.list;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import com.star.common.richclient.factory.control.AbstractFControl;
import com.star.common.richclient.utils.list.PropertyLabelProvider;

public class FList extends AbstractFControl {
	
	private IStructuredContentProvider contentProvider;
	
	private IBaseLabelProvider labelProvider;

	private String renderPropertyName;

	public FList() {
	}

	public FList(int style) {
		super(style);
	}

	public List doCreateControl(Composite parent) {
		ListViewer viewer = new ListViewer(parent, getStyle());
		List list = viewer.getList();
		
		list.setData(viewer);
		
		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(getLabelProvider());

		return list;
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