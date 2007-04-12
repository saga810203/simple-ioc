package com.star.common.ioc.support.interpreter;

import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.Node;

/**
 * 扩展点注入，但仅允许注入一个扩展。如：
 * 
 * <pre>
 *  a :	com.myace.Parent
 *  	-child : (single-extend-point)ep
 *  ep  : (string)abc
 * <br>
 * 将Id为“ep”的bean设置到a的child属性中。
 * 注意可以在父BeanFactory定义扩展点，在子BeanFactory中定义扩展。
 * </pre>
 * 
 * @author liuwei
 * @version 1.0
 */
public class SingleExtendPointNodeIterpreter extends AbstractNodeInterpreter {

	private static final Object NO_FOUND =new Object();
	
	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Object extend = findExtend(valueString, beanFactory);
		return extend==NO_FOUND?null:extend;
	}

	private Object findExtend(String beanId, BeanFactory bf) {
		if(bf.containsBean(beanId)){
			return bf.getBean(beanId);
		}
		if (bf instanceof DefaultBeanFactory) {
			List<BeanFactory> bfs = ((DefaultBeanFactory) bf)
					.getChildren();
			for (BeanFactory c : bfs) {
				Object extend = findExtend(beanId, c);
				if(extend!=NO_FOUND){
					return extend;
				}
			}
		}
		return NO_FOUND;
	}

}
