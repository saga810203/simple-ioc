package com.star.common.richclient.form.support;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Combo;

import com.star.common.richclient.form.IValueControl;
import com.star.common.richclient.form.FormHelp.ContentModifyListener;

public class ComboValueControl implements IValueControl<Combo> {

	private static final String VIEWER_KEY = "viewer";
	
	public void addModifyListener(final ContentModifyListener listener, Combo c) {
		ComboViewer cv = (ComboViewer) c.getData(VIEWER_KEY);
		cv.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				listener.onModify();

			}
		});

	}

	public Object getValue(Combo c) {
		ComboViewer cv = (ComboViewer) c.getData(VIEWER_KEY);
		StructuredSelection selection = (StructuredSelection) cv
				.getSelection();
		return selection.getFirstElement();
	}

	public void setValue(Combo c, Object propertyValue) {
		ComboViewer cv = (ComboViewer) c.getData(VIEWER_KEY);
		StructuredSelection selection = null;
		if (propertyValue == null) {
			selection = new StructuredSelection();
		} else {
			selection = new StructuredSelection(propertyValue);
		}
		cv.setSelection(selection);
	}

}