package com.star.common.richclient.utils.table;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * 表工具类.
 * 
 * @author liuwei
 * 
 */
public class TableUtils {

	public static TableViewer create(Composite parent, boolean hasCheckedColumn) {
		TableViewer viewer = null;
		if (hasCheckedColumn) {
			viewer = CheckboxTableViewer.newCheckList(parent, SWT.MULTI
					| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		} else {
			viewer = new TableViewer(parent);
		}
		viewer = new TableViewer(parent);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		return viewer;
	}
}