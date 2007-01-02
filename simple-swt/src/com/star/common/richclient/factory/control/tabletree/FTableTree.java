package com.star.common.richclient.factory.control.tabletree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

import com.star.common.richclient.factory.control.AbstractFControl;
import com.star.common.richclient.factory.control.table.CellModifierByColumn;
import com.star.common.richclient.factory.control.table.FTableColumn;
import com.star.common.richclient.factory.control.table.TableLabelProviderByColumn;

public class FTableTree extends AbstractFControl {
	
	public static final String VIEWER_KEY = "viewer";

	private List<FTableColumn> columnList = new ArrayList<FTableColumn>();

	private boolean hasCheckedColumn;

	private IContentProvider contentProvider;

	private IBaseLabelProvider labelProvider;

	private ICellModifier cellModifier;
	
	public FTableTree clone() {
		FTableTree c = (FTableTree) super.clone();
		c.columnList = new ArrayList<FTableColumn>(columnList);
		return c;
	}
	
	public FTableTree() {
		// SINGLE, MULTI, CHECK, FULL_SELECTION
		super(SWT.BORDER | SWT.SINGLE);
	}

	public FTableTree(int style) {
		super(style);
	}

	@Override
	protected Control doCreateControl(Composite parent) {

		TreeViewer viewer = createViewer(parent);
		Tree tree = viewer.getTree();
		tree.setData(VIEWER_KEY,viewer);
		
		viewer.setColumnProperties(getPropertyNames());
		
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

	public void addColumn(FTableColumn column) {
		this.columnList.add(column);
	}
	
	public boolean hasProperty(String propertyName){
		for (FTableColumn col : columnList) {
			if(propertyName.equals(col.getColumnName())){
				return true;
			} 
		}
		return false;
	}
	
	public IContentProvider getContentProvider() {
		return contentProvider;
	}

	public void setContentProvider(IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public boolean isHasCheckedColumn() {
		return hasCheckedColumn;
	}

	public void setHasCheckedColumn(boolean hasCheckedColumn) {
		this.hasCheckedColumn = hasCheckedColumn;
	}
	
	private String[] getPropertyNames() {
		int n = getColumnList().size();
		String[] propNames = new String[n];
		for (int i = 0; i < n; i++) {
			FTableColumn c = getColumnList().get(i);
			propNames[i] = c.getPropertyName();

		}
		return propNames;
	}

	public IBaseLabelProvider getLabelProvider() {
		if (labelProvider == null)
			labelProvider = new TableLabelProviderByColumn(getColumnList());
		return labelProvider;
	}

	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public List<FTableColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<FTableColumn> columnList) {
		this.columnList = columnList;
	}

	public ICellModifier getCellModifier() {
		if (cellModifier == null)
			cellModifier = new CellModifierByColumn(getColumnList());
		return cellModifier;
	}

	public void setCellModifier(ICellModifier cellModifier) {
		this.cellModifier = cellModifier;
	}
}
