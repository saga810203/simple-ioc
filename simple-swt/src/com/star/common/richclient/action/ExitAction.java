package com.star.common.richclient.action;

import org.eclipse.jface.action.Action;

import com.star.common.ioc.StaticBeanFactory;
import com.star.common.richclient.Application;
/**
 * �˳���������
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
	 * ��ע��ApplicationΪ�˷�ֹѭ������
	 */
	protected Application getApplication() {
		return StaticBeanFactory.getBean("application",Application.class);
	}
}
