package com.star.common.richclient.factory;

import org.eclipse.swt.widgets.Control;

/**
 * ControlFactory的创建监听器。
 * @author liuwei
 */
public interface ControlCreateListener {
	
	/**
	 * 当控件将要创建时被调用。
	 * @param factory
	 */
	void preCreate(IControlFactory factory);
	
	/**
	 * 当控件创建后被调用。
	 * 如果为CompositeFactory，则在它的全部子控件创建后被调用。
	 * @param factory
	 * @param control
	 */
	void postCreate(IControlFactory factory, Control control);
	
}
