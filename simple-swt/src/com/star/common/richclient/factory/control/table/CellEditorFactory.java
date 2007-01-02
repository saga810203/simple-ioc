package com.star.common.richclient.factory.control.table;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Table;

public interface CellEditorFactory {
	
	CellEditor create(Table table);
	
}
