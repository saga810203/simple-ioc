package com.star.common.ioc.support;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.BasicNodeInterpreters;
import com.star.common.util.Node;

/**
 * �ڵ��������
 * @see BasicNodeInterpreters
 * @author liuwei
 * @version 1.0
 */
public interface NodeInterpreter {

    /**
     * ����bean����������һ������
     * @param node bean�ڵ�
     * @param valueString �ڵ��value���ֽ����ַ���
     * @param interpreterName ����������
     * @param context �ɿ�ԽNode��������
     * @param beanFactory ��ǰ��beanFactory
     * @return �������Ķ���
     */
	Object createObject(Node node, String valueString, String interpreterName, Map<Object, Object> context, BeanFactory beanFactory);
}
