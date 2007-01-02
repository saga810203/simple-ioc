package com.star.common.richclient.factory;

import org.eclipse.swt.widgets.Control;

/**
 * ControlFactory�Ĵ�����������
 * @author liuwei
 */
public interface ControlCreateListener {
	
	/**
	 * ���ؼ���Ҫ����ʱ�����á�
	 * @param factory
	 */
	void preCreate(IControlFactory factory);
	
	/**
	 * ���ؼ������󱻵��á�
	 * ���ΪCompositeFactory����������ȫ���ӿؼ������󱻵��á�
	 * @param factory
	 * @param control
	 */
	void postCreate(IControlFactory factory, Control control);
	
}
