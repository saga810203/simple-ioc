package com.star.common.ioc;

import java.util.List;
import java.util.Map;

import com.star.common.util.Node;

/**
 * 
 * 对象工厂。 简单的ioc实现，可以实现单例的管理和对象注入。
 * 
 * @author liuwei
 * @version 1.1
 */
public interface BeanFactory {

    /**
     * 得到bean
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
     * 得到bean。传入一个上下文对象，构造bean时使用。
     * 
     * @param <T>
     * @param beanId
     * @param context
     * @param requiredType
     * @return
     */
    Object getBean(String beanId, Map<Object, Object> context);

    /**
     * 得到需要类型的bean。传入一个上下文对象，构造bean时使用。
     * 
     * @param <T>
     * @param beanId
     * @param context
     * @param requiredType
     * @return
     */
    <T> T getBean(String beanId, Map<Object, Object> context, Class<T> requiredType);

    /**
     * 判断是否存在指定beanId的bean
     * 
     * @param beanId
     * @return
     */
    boolean containsBean(String beanId);

    Object getBean(Node node);

    Object getBean(Node node, Map<Object, Object> context);

    /**
     * 得到node对应的对象。通过传入valueString，可以递规的处理node.value中包含的前缀“(...)”。 <br>
     * 如：node.value="(a)(b)(c)text"，首先node将被a节点解释器处理，
     * 此时a节点解释器，可以调用本方法并将它处理时的valueString（此时应为"(b)(c)text"）传入， 就可以使用b阶段解释器处理节点。
     * 
     * @param node
     *            被处理的节点
     * @param valueString
     *            处理的value字符串
     * @param context 上下文
     * @return
     */
    Object getBean(Node node, String valueString, Map<Object, Object> context);

    /**
     * 得到所有bean的Id。
     * 
     * @return beanId数组，不可能为null
     */
    String[] getBeanIds();

    /**
     * 按照类型得到bean数组。
     * @param type 对象类型
     * @return beanId数组
     */
    <T> List<T> getBeansByType(Class<T> type);

    /**
     * 按照类型得到bean。当存在多个该类型的bean，或不存在该类型的bean时，抛出异常。
     * @param type 对象类型
     * @return bean
     */
    <T> T getBeanByType(Class<T> type);

}