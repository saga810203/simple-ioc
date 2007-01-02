package com.star.common.richclient.form.support;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.star.common.richclient.form.IValueControl;
import com.star.common.richclient.form.FormHelp.ContentModifyListener;


public class TextValueControl implements IValueControl<Text> {

	public void addModifyListener(final ContentModifyListener listener, Text c) {
		c.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				listener.onModify();
			}
		});

	}

	public String getValue(Text c) {
		return c.getText().trim();
	}

	public void setValue(Text c, Object propertyValue) {
		c.setText(propertyValue == null ? "" : propertyValue.toString());
	}

}
