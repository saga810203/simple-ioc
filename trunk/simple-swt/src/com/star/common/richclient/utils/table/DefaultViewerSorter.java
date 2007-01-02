package com.star.common.richclient.utils.table;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeColumn;

import com.star.common.richclient.Constant;
import com.star.common.util.bean.PropertyUtils;

/**
 * 默认的表视图排序器.
 * 
 * @author liuwei
 * 
 */
public class DefaultViewerSorter extends ViewerSorter {

	private int columnIndex;

	private String propertyName;

	private boolean ascending;

	/**
	 * 为表视图加入排序器.
	 * 
	 * @param viewer
	 *            表视图
	 */
	public void installSorter(TableViewer viewer) {
		viewer.setSorter(this);

		SelectionListener listener = new SortSelectionAdapter(viewer, this);
		for (TableColumn col : viewer.getTable().getColumns()) {
			col.addSelectionListener(listener);
		}
	}

	/**
	 * 为表视图加入排序器.
	 * 
	 * @param viewer
	 *            表视图
	 */
	public void installSorter(TreeViewer viewer) {
		viewer.setSorter(this);

		SelectionListener listener = new SortSelectionAdapter(viewer, this);
		for (TreeColumn col : viewer.getTree().getColumns()) {
			col.addSelectionListener(listener);
		}
	}

	public int compare(Viewer viewer, Object e1, Object e2) {
		if (propertyName == null) {
			return 0;
		}
		Object a1 = null;
		Object a2 = null;
		try {
			a1 = PropertyUtils.getProperty(e1, propertyName);
			a2 = PropertyUtils.getProperty(e2, propertyName);
		} catch (Exception e) {
			// nothing;
		}

		if (a1 == null) {
			a1 = "";
		}
		if (a2 == null) {
			a2 = "";
		}

		int n = super.compare(viewer, a1, a2);
		return ascending ? n : (-1 * n);
	}

	// public boolean isAscending() {
	// return ascending;
	// }
	//
	// public void setAscending(boolean ascending) {
	// this.ascending = ascending;
	// }

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		if (propertyName != null && propertyName.equals(this.propertyName)) {
			ascending = !ascending;
		} else {
			ascending = true;
		}
		this.propertyName = propertyName;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int colIndex) {
		this.columnIndex = colIndex;
	}

	public static class SortSelectionAdapter extends SelectionAdapter {
		private Viewer viewer;

		private DefaultViewerSorter sorter;

		public SortSelectionAdapter(Viewer viewer, DefaultViewerSorter sorter) {
			this.viewer = viewer;
			this.sorter = sorter;
		}

		public void widgetSelected(SelectionEvent e) {
			Item col = (Item) e.getSource();
			int columnIndex = ((Integer) col.getData(Constant.COLUMN_INDEX))
					.intValue();
			String propertyName = (String) col.getData(Constant.PROPERTY);

			// TODO 排序后标题图标的变化
			sorter.setColumnIndex(columnIndex);
			sorter.setPropertyName(propertyName);

			viewer.refresh();
		}
	}
}
