package com.star.common.richclient.form;

import org.eclipse.swt.widgets.Control;

/**
 * @author myace
 * @version 1.0
 */
public interface IValidator {

	String validate(String property, Object newPropertyValue, Control control);
	
}
