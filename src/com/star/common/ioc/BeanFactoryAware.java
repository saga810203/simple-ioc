package com.star.common.ioc;

/**
 * BeanFactory֪���߽ӿڡ� ʵ�ָýӿڵ�����BeanFactory�д���ʱ��beanFactory�����øýӿڵķ����������Լ���Ϊ�������롣
 * 
 * @author liuwei
 * @version 1.0
 */
public interface BeanFactoryAware {
	
	void setBeanFactory(BeanFactory beanFactory);

}
