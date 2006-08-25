package com.star.common.ioc;

import java.util.Map;

import com.star.common.util.Node;

/**
 * 静态BeanFactory。 提供BeanFactory对应的静态方法。
 * 
 * @author liuwei
 * @version 1.0
 */
public class StaticBeanFactory {
	
	public static final String DEFAULT_INTERPRETER_CONFIG_FILE = BeanFactoryUtils.DEFAULT_INTERPRETER_CONFIG_FILE;
		
	private static BeanFactory instance;

	private StaticBeanFactory() {

	}

	public static void setBeanFactory(BeanFactory beanFactory) {
		instance = beanFactory;
	}

	public static Object getBean(String beanId) {
		return instance.getBean(beanId);
	}

	public static <T> T getBean(String beanId, Class<T> requiredType) {
		return instance.getBean(beanId, requiredType);
	}

	public static <T> T getBean(String beanId, Map<Object, Object> context,
			Class<T> requiredType) {
		return instance.getBean(beanId, context, requiredType);
	}

	public static Object getBean(String beanId, Map<Object, Object> context) {
		return instance.getBean(beanId, context);
	}

	public static boolean containsBean(String beanId) {
		return instance.containsBean(beanId);
	}

	public static BeanFactory getBeanFactory() {
		return instance;
	}
	/**
	 * 检查是否已经初始化完毕。
	 * 
	 * @return
	 */
	public static boolean isInited() {
		return instance != null;
	}

	//=====================================
	
	public static void init(String... configFilePaths) {
		instance = BeanFactoryUtils.create(configFilePaths);
	}

	public static void init(BeanFactory parent, String... configFilePaths) {
		instance = BeanFactoryUtils.createButNoDefaultConfig(parent, configFilePaths);
	}

	public static void init(Class location, String... configFilePaths) {
		instance = BeanFactoryUtils.create(location, configFilePaths);
	}

	/**
	 * 初始化BeanFactory。
	 * 
	 * @param configFilePath
	 *            beanFactory的配置文件路径
	 */
	public static void init(BeanFactory parent, Class location,
			String... configFilePaths) {
		instance = BeanFactoryUtils.createButNoDefaultConfig(parent, location, configFilePaths);
	}

	public static void init(Map<String, Node> configTreeMap) {
		instance = BeanFactoryUtils.create(configTreeMap);
	}

	public static void init(Map<String, Node> configTreeMap, BeanFactory parent) {
		instance = BeanFactoryUtils.create(configTreeMap, parent);
	}


}
