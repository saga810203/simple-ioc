package com.star.common.richclient.support;

/**
 * 消息源
 * 
 * @author liuwei
 * 
 */
public interface MessageSource {

	/**
	 * 根据参数从资源文件中提取相应消息字符串
	 * 
	 * @param key
	 *            消息键值
	 * @return 没找到则返回null
	 */
	String getMessage(String key);

	/**
	 * 根据参数从资源文件中提取相应消息字符串
	 * 
	 * @param key
	 *            消息键值
	 * @param arg
	 *            消息参数
	 * @return 没找到则返回null
	 */
	String getMessage(String key, Object[] arg);

	/**
	 * 根据参数从资源文件中提取相应消息字符串
	 * 
	 * @param key
	 *            消息键值
	 * @param defaultMessage
	 *            默认消息
	 * @param arg
	 *            消息参数
	 * @return 没找到则返回defaultMessage
	 */
	String getMessage(String key, String defaultMessage, Object... arg);
}
