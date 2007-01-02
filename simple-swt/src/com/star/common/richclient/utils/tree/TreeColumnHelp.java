package com.star.common.richclient.utils.tree;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;

import com.star.common.richclient.Constant;
import com.star.common.richclient.utils.list.PropertyLabelProvider;
import com.star.common.richclient.utils.table.DefaultViewerSorter;
import com.star.common.richclient.utils.table.PropertyTableLabelProvider;

/**
 * @author myace
 * @version 1.0
 */
public class TreeColumnHelp {

	private final TreeViewer viewer;

	public TreeColumnHelp(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public TreeColumnHelp(Composite parent) {
		viewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
	}

	public TreeColumnHelp add(String property, String columnName, int width) {
		TreeColumn column = new TreeColumn(viewer.getTree(), SWT.NONE);
		column.setWidth(width);
		column.setText(columnName);
		column.setData(Constant.PROPERTY, property);
		return this;
	}
	/**
	 * 多列表创建方法.
	 * 首先应当使用add方法加入表中的列.
	 * @return 
	 */
	public TreeViewer produce() {
		viewer.setColumnProperties(getProperties());
		viewer.setLabelProvider(getTableLabelProvider());
		new DefaultViewerSorter().installSorter(viewer);
		return viewer;
	}

	protected ITableLabelProvider getTableLabelProvider() {
		return new PropertyTableLabelProvider(getProperties());
	}
	/**
	 * 单列表创建方法.
	 * 创建前不应使用add方法.
	 * @param renderPropertyName 显示属性
	 * @return
	 */
	public TreeViewer produce(String renderPropertyName) {
		viewer.setColumnProperties(getProperties());
		viewer.setLabelProvider(new PropertyLabelProvider(renderPropertyName));
		new DefaultViewerSorter().installSorter(viewer);
		return viewer;
	}

	public String[] getProperties() {
		TreeColumn[] cols = viewer.getTree().getColumns();
		String[] properties = new String[cols.length];

		for (int i = 0; i < cols.length; i++) {
			TreeColumn column = cols[i];
			properties[i] = (String) column.getData(Constant.PROPERTY);
		}
		return properties;
	}

	public TreeViewer getViewer() {
		return viewer;
	}

}
