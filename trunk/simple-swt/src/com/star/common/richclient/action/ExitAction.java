package com.star.common.richclient.action;

import org.eclipse.jface.action.Action;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.richclient.Application;
/**
 * 退出程序动作。
 * @author liuwei
 * @version 1.0
 */
public class ExitAction extends Action {

	/**
	 * Exits the application
	 */
	public void run() {
		getApplication().close();
	}
	
	//==================================
	/**
	 * 不注入Application为了防止循环依赖
	 */
	protected Application getApplication() {
		return StaticBeanFactory.getBean("application",Application.class);
	}
}
