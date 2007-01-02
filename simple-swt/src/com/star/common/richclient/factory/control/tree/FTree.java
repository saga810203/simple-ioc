package com.star.common.richclient.factory.control.tree;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

import com.star.common.richclient.Constant;
import com.star.common.richclient.factory.control.AbstractFControl;
import com.star.common.richclient.utils.list.PropertyLabelProvider;

public class FTree extends AbstractFControl {

	private boolean hasCheckedColumn;

	private String renderPropertyName;

	private IContentProvider contentProvider;

	private IBaseLabelProvider labelProvider;

	public FTree() {
		// SINGLE, MULTI, CHECK, FULL_SELECTION
		super(SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	public FTree(int style) {
		super(style);
	}

	@Override
	protected Control doCreateControl(Composite parent) {

		TreeViewer viewer = createViewer(parent);
		Tree tree = viewer.getTree();
		tree.setData(Constant.VIEWER, viewer);

		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(getLabelProvider());

		return tree;
	}

	private TreeViewer createViewer(Composite parent) {
		TreeViewer viewer = null;
		if (hasCheckedColumn) {
			viewer = new CheckboxTreeViewer(parent, getStyle());
		} else {
			viewer = new TreeViewer(parent, getStyle());
		}
		return viewer;
	}

	public IContentProvider getContentProvider() {
		return contentProvider;
	}

	public void setContentProvider(IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public IBaseLabelProvider getLabelProvider() {
		if (labelProvider == null) {
			labelProvider = new PropertyLabelProvider(getRenderPropertyName());
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

	public boolean isHasCheckedColumn() {
		return hasCheckedColumn;
	}

	public void setHasCheckedColumn(boolean hasCheckedColumn) {
		this.hasCheckedColumn = hasCheckedColumn;
	}

}
