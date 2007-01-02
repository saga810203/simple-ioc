package com.star.common.richclient.factory.control.table;

import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableItem;

/**
 * 根据FTableColumn对象的定义决定单元格编辑的ICellModifier.
 * 
 * @author liuwei
 * 
 */
public class CellModifierByColumn implements ICellModifier {

	private List<FTableColumn> columnList;

	public CellModifierByColumn() {
	}

	public CellModifierByColumn(List<FTableColumn> columnList) {
		this.columnList = columnList;
	}

	public boolean canModify(Object element, String property) {
		return getColumn(property).isCanModify();
	}

	public Object getValue(Object element, String property) {
		return getColumn(property).getColumnValue(element);
	}

	public void modify(Object element, String property, Object value) {
		TableItem item = ((TableItem) element);
		Object object = ((TableItem) element).getData();
		getColumn(property).setColumnValue(object, value);
		TableViewer tableViewer = (TableViewer) item.getParent().getData(
				FTable.VIEWER_KEY);
		tableViewer.update(object,null);

	}

	private FTableColumn getColumn(String property) {
		for (FTableColumn col : columnList) {
			if (property.equals(col.getPropertyName())) {
				return col;
			}
		}
		return null;
	}

	public void setColumnList(List<FTableColumn> columnList) {
		this.columnList = columnList;
	}

}
