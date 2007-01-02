package com.star.common.richclient.support;

import org.eclipse.jface.action.CoolBarManager;
/**
 * CoolBarManager 工厂
 * @author liuwei
 * @version 1.0
 * @see org.eclipse.jface.action.CoolBarManager
 */
public interface CoolBarManagerFactory {

	/**
	 * 创建 CoolBarManager
	 * @see CoolBarManager#CoolBarManager(int style)
	 */
	CoolBarManager createCoolBarManager(int style);
	
	//boolean hasCoolBar();
}
