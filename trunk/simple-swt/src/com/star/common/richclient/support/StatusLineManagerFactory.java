package com.star.common.richclient.support;

import org.eclipse.jface.action.StatusLineManager;
/**
 * StatusLineManager 工厂
 * @author liuwei
 * @version 1.0
 * @see org.eclipse.jface.action.StatusLineManager
 */
public interface StatusLineManagerFactory {

	/**
	 * 创建 StatusLineManager
	 */
	StatusLineManager createStatusLineManager();
	
	
	//boolean hasStatusLine();
}
