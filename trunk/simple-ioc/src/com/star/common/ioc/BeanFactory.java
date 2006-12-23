package com.star.common.ioc;

import java.util.Map;

import com.star.common.util.Node;

/**
 * 
 * ���󹤳��� �򵥵�iocʵ�֣�����ʵ�ֵ����Ĺ���Ͷ���ע�롣
 * 
 * @author liuwei
 * @version 1.1
 */
public interface BeanFactory {

    /**
     * �õ�bean
     * 
     * @param beanId
     * @return
     */
    Object getBean(String beanId);

    /**
     * Get a required type's Bean by Id.
     * 
     * @param <T>
     * @param beanId
     * @param requiredType
     * @return
     */
    <T> T getBean(String beanId, Class<T> requiredType);

    /**
     * �õ�bean������һ�������Ķ��󣬹���beanʱʹ�á�
     * 
     * @param <T>
     * @param beanId
     * @param context
     * @param requiredType
     * @return
     */
    Object getBean(String beanId, Map<Object, Object> context);

    /**
     * �õ���Ҫ���͵�bean������һ�������Ķ��󣬹���beanʱʹ�á�
     * 
     * @param <T>
     * @param beanId
     * @param context
     * @param requiredType
     * @return
     */
    <T> T getBean(String beanId, Map<Object, Object> context, Class<T> requiredType);

    /**
     * �ж��Ƿ����ָ��beanId��bean
     * 
     * @param beanId
     * @return
     */
    boolean containsBean(String beanId);

    Object getBean(Node node);

    Object getBean(Node node, Map<Object, Object> context);

    /**
     * �õ�node��Ӧ�Ķ���ͨ������valueString�����Եݹ�Ĵ���node.value�а�����ǰ׺��(...)���� <br>
     * �磺node.value="(a)(b)(c)text"������node����a�ڵ����������
     * ��ʱa�ڵ�����������Ե��ñ���������������ʱ��valueString����ʱӦΪ"(b)(c)text"�����룬 �Ϳ���ʹ��b�׶ν���������ڵ㡣
     * 
     * @param node
     *            ������Ľڵ�
     * @param valueString
     *            �����value�ַ���
     * @param context
     * @return
     */
    Object getBean(Node node, String valueString, Map<Object, Object> context);

    /**
     * �õ�����bean�����ơ�
     * 
     * @return bean�������飬������Ϊnull
     */
    String[] getBeanNames();

}