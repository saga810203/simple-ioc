package com.star.common.richclient.factory.control.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;

import com.star.common.richclient.factory.control.AbstractFControl;
import com.star.common.richclient.utils.table.DefaultViewerSorter;

/**
 * 表控件工厂.
 * 
 * @author liuwei
 * 
 */
public class FTable extends AbstractFControl {

	public static final String VIEWER_KEY = "viewer";

	private List<FTableColumn> columnList = new ArrayList<FTableColumn>();

	private boolean hasCheckedColumn;

	private IBaseLabelProvider labelProvider;

	private IStructuredContentProvider contentProvider;

	private DefaultViewerSorter sorter;

	private ICellModifier cellModifier;

	public FTable clone() {
		FTable c = (FTable) super.clone();
		c.columnList = new ArrayList<FTableColumn>(columnList);
		return c;
	}

	public FTable() {
		super(SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI
				| SWT.FULL_SELECTION);
	}

	public FTable(int style) {
		super(style);
	}

	public void addColumn(FTableColumn column) {
		this.columnList.add(column);
	}

	public boolean hasProperty(String propertyName) {
		for (FTableColumn col : columnList) {
			if (propertyName.equals(col.getColumnName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Control doCreateControl(Composite parent) {
		TableViewer viewer = createTableView(parent);
		Table table = viewer.getTable();
		table.setData(VIEWER_KEY, viewer);
		setTableLayout(table);

		for (int i = 0; i < getColumnList().size(); i++) {
			FTableColumn c = getColumnList().get(i);
			c.addTo(table);
		}
		viewer.setCellModifier(getCellModifier());
		viewer.setCellEditors(getCellEditors(table));
		viewer.setColumnProperties(getPropertyNames());
		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(getLabelProvider());

		getSorter().installSorter(viewer);

		return viewer.getTable();
	}

	private TableViewer createTableView(Composite parent) {
		TableViewer viewer = null;
		if (isHasCheckedColumn()) {
			viewer = CheckboxTableViewer.newCheckList(parent, getStyle());
		} else {
			viewer = new TableViewer(parent, getStyle());
		}
		return viewer;
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

	private CellEditor[] getCellEditors(Table table) {
		int n = getColumnList().size();
		CellEditor[] editors = new CellEditor[n];
		for (int i = 0; i < n; i++) {
			FTableColumn c = getColumnList().get(i);
			editors[i] = c.create(table);
		}
		return editors;
	}

	public boolean isHasCheckedColumn() {
		return hasCheckedColumn;
	}

	public void setHasCheckedColumn(boolean hasCheckedColumn) {
		this.hasCheckedColumn = hasCheckedColumn;
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
		if (labelProvider == null)
			labelProvider = new TableLabelProviderByColumn(getColumnList());
		return labelProvider;
	}

	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	public DefaultViewerSorter getSorter() {
		if (sorter == null)
			sorter = new DefaultViewerSorter();
		return sorter;
	}

	public void setSorter(DefaultViewerSorter sorter) {
		this.sorter = sorter;
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

	/**
	 * 设置默认的表布局.
	 * 
	 * @param table
	 *            表
	 */
	public void setTableLayout(Table table) {
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableLayout layout = new TableLayout();
		table.setLayout(layout);
	}

}
