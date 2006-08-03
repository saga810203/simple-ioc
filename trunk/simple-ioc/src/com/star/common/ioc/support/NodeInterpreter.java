package com.star.common.ioc.support;

import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.BasicNodeInterpreters;
import com.star.common.util.Node;

/**
 * 节点解释器。
 * @see BasicNodeInterpreters
 * @author liuwei
 * @version 1.0
 */
public interface NodeInterpreter {

    /**
     * 根据bean的描述创建一个对象。
     * @param node bean节点
     * @param valueString 节点的value被分解后的字符串
     * @param interpreterName 解释器名称
     * @param context 可跨越Node的上下文
     * @param beanFactory 当前的beanFactory
     * @return 创建出的对象
     */
	Object createObject(Node node, String valueString, String interpreterName, Map<Object, Object> context, BeanFactory beanFactory);
}
