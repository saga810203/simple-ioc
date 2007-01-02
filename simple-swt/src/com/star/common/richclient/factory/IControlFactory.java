package com.star.common.richclient.factory;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
/**
 * 控件工厂。通过createControl方法可以创建控件。
 * 由于swt的控件创建必须依赖已经创建好的父控件，这样降低了编写程序的灵活性，
 * 因此引入此接口，将控件的创建过程抽象出来。
 * 这样控件的定义和真正创建相分离。
 * @author liuwei
 *
 */
public interface IControlFactory extends Cloneable {
	
	/**
	 * 返回（布局）函数唯一标识
	 * 
	 * @return Id
	 */
	String getId();
	
	void setId(String id);
	
	/**
	 * 创建控件。
	 * @param parent 父控件
	 * @return
	 */
	Control createControl(Composite parent);
	
	void setAttribute(String key,Object attribute);
	
	Object getAttribute(String key);
	
	/**
	 * 添加创建监听器。
	 * @param listener
	 */
	void addCreateListener(ControlCreateListener listener);
	
	void removeCreateListener( ControlCreateListener listener);
	
	void removeAllCreateListener();
	
	IControlFactory clone();
}
