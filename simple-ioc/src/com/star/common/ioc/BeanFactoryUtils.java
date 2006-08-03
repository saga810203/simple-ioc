package com.star.common.ioc;

import java.util.Map;

import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.Node;
import com.star.common.util.TreeMap;

public class BeanFactoryUtils {
	public static final String DEFAULT_INTERPRETER_CONFIG_FILE = "/com/star/common/ioc/support/default-interpreter-config.properties";

	private static final String CONFIG_FILE = "bean-config.properties";

	public static BeanFactory create(String... configFilePaths) {
		return create(null, null, configFilePaths);
	}

	public static BeanFactory create(BeanFactory parent,
			String... configFilePaths) {
		return create(parent, null, configFilePaths);
	}

	public static BeanFactory create(Class location, String... configFilePaths) {
		return create(null, location, configFilePaths);
	}

	/**
	 * 初始化BeanFactory。
	 * 
	 * @param configFilePath
	 *            beanFactory的配置文件路径
	 */
	public static BeanFactory create(BeanFactory parent, Class location,
			String... configFilePaths) {
		String[] configs = null;

		if (configFilePaths == null || configFilePaths.length == 0) {
			configs = new String[] { DEFAULT_INTERPRETER_CONFIG_FILE,
					CONFIG_FILE };
		} else {
			configs = new String[configFilePaths.length + 1];
			configs[0] = DEFAULT_INTERPRETER_CONFIG_FILE;
			System.arraycopy(configFilePaths, 0, configs, 1,
					configFilePaths.length);
		}

		return create(new TreeMap().load(location, configs), parent);
	}

	public static BeanFactory create(Map<String, Node> configTreeMap) {
		return create(configTreeMap, null);
	}

	public static BeanFactory create(Map<String, Node> configTreeMap,
			BeanFactory parent) {
		DefaultBeanFactory beanFactory = new DefaultBeanFactory(configTreeMap);
		beanFactory.setParent(parent);
		return beanFactory;
	}
}
