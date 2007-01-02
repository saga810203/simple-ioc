package com.star.common.richclient.form;

import org.eclipse.swt.widgets.Control;

import com.star.common.richclient.form.FormHelp.ContentModifyListener;

/**
 * @author myace
 * @version 1.0
 */
public interface IValueControl<T extends Control> {

	void setValue(T c, Object propertyValue);

	Object getValue(T c);
	
	void addModifyListener(ContentModifyListener listener, T c);
}
