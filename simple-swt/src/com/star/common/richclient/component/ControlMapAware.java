package com.star.common.richclient.component;

import java.util.Map;

import org.eclipse.swt.widgets.Control;

public interface ControlMapAware {
	void setControlMap(Map<String,Control> controlMap);
}
