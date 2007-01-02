package com.star.common.richclient.support;

/**
 * ��ϢԴ
 * 
 * @author liuwei
 * 
 */
public interface MessageSource {

	/**
	 * ���ݲ�������Դ�ļ�����ȡ��Ӧ��Ϣ�ַ���
	 * 
	 * @param key
	 *            ��Ϣ��ֵ
	 * @return û�ҵ��򷵻�null
	 */
	String getMessage(String key);

	/**
	 * ���ݲ�������Դ�ļ�����ȡ��Ӧ��Ϣ�ַ���
	 * 
	 * @param key
	 *            ��Ϣ��ֵ
	 * @param arg
	 *            ��Ϣ����
	 * @return û�ҵ��򷵻�null
	 */
	String getMessage(String key, Object[] arg);

	/**
	 * ���ݲ�������Դ�ļ�����ȡ��Ӧ��Ϣ�ַ���
	 * 
	 * @param key
	 *            ��Ϣ��ֵ
	 * @param defaultMessage
	 *            Ĭ����Ϣ
	 * @param arg
	 *            ��Ϣ����
	 * @return û�ҵ��򷵻�defaultMessage
	 */
	String getMessage(String key, String defaultMessage, Object... arg);
}
