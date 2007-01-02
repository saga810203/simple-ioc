package com.star.common.richclient.utils.table;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import com.star.common.richclient.Constant;

/**
 * @author myace
 * @version 1.0
 */
public class TableColumnHelp {

	private final TableViewer viewer;

	public TableColumnHelp(TableViewer viewer) {
		this.viewer = viewer;
	}

	public TableColumnHelp(Composite parent) {
		viewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
	}

	public TableColumnHelp add(String property, String columnName, int width) {
		TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setWidth(width);
		column.setText(columnName);
		column.setData(Constant.PROPERTY, property);
		return this;
	}

	public TableViewer produce() {
		viewer.setColumnProperties(getProperties());
		viewer.setLabelProvider(getTableLabelProvider());
		viewer.setContentProvider(new ArrayContentProvider());
		new DefaultViewerSorter().installSorter(viewer);
		return viewer;
	}

	protected ITableLabelProvider getTableLabelProvider() {
		return new PropertyTableLabelProvider(getProperties());
	}

	public String[] getProperties() {
		TableColumn[] cols = viewer.getTable().getColumns();
		String[] properties = new String[cols.length];

		for (int i = 0; i < cols.length; i++) {
			TableColumn column = cols[i];
			properties[i] = (String) column.getData(Constant.PROPERTY);
		}
		return properties;
	}

	public TableViewer getViewer() {
		return viewer;
	}

}
