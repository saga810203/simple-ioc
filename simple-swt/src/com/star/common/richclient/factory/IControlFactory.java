package com.star.common.richclient.factory;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
/**
 * �ؼ�������ͨ��createControl�������Դ����ؼ���
 * ����swt�Ŀؼ��������������Ѿ������õĸ��ؼ������������˱�д���������ԣ�
 * �������˽ӿڣ����ؼ��Ĵ������̳��������
 * �����ؼ��Ķ����������������롣
 * @author liuwei
 *
 */
public interface IControlFactory extends Cloneable {
	
	/**
	 * ���أ����֣�����Ψһ��ʶ
	 * 
	 * @return Id
	 */
	String getId();
	
	void setId(String id);
	
	/**
	 * �����ؼ���
	 * @param parent ���ؼ�
	 * @return
	 */
	Control createControl(Composite parent);
	
	void setAttribute(String key,Object attribute);
	
	Object getAttribute(String key);
	
	/**
	 * ��Ӵ�����������
	 * @param listener
	 */
	void addCreateListener(ControlCreateListener listener);
	
	void removeCreateListener( ControlCreateListener listener);
	
	void removeAllCreateListener();
	
	IControlFactory clone();
}
