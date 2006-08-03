package com.star.common.ioc;

/**
 * BeanFactory知道者接口。 实现该接口的类在BeanFactory中创建时，beanFactory将调用该接口的方法，并把自己最为参数传入。
 * 
 * @author liuwei
 * @version 1.0
 */
public interface BeanFactoryAware {
	
	void setBeanFactory(BeanFactory beanFactory);

}
