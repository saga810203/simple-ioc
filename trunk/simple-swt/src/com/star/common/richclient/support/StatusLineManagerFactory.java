package com.star.common.richclient.support;

import org.eclipse.jface.action.StatusLineManager;
/**
 * StatusLineManager ����
 * @author liuwei
 * @version 1.0
 * @see org.eclipse.jface.action.StatusLineManager
 */
public interface StatusLineManagerFactory {

	/**
	 * ���� StatusLineManager
	 */
	StatusLineManager createStatusLineManager();
	
	
	//boolean hasStatusLine();
}
