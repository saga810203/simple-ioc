package com.star.common.richclient.support;

import org.eclipse.jface.action.MenuManager;
/**
 * MenuBarManager 工厂
 * @author liuwei
 * @version 1.0
 * @see org.eclipse.jface.action.MenuManager
 */
public interface MenuBarManagerFactroy {
	
	/**
	 * 创建 MenuBarManager
	 */
	MenuManager createMenuBarManager();
	
	
	//boolean hasMenuBar();
	
}
