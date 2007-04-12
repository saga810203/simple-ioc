package com.star.common.ioc.support.interpreter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.Node;

/**
 * 扩展点注入。如：
 * 
 * <pre>
 *  a :	com.myace.Parent
 *  	-children : (extend-point)ep.*
 *  ep1  : (string)abc 
 *  ep2  : (string)def
 * <br>
 * 以“ep”为前缀的bean将合并为一个List对象并被设置到a的children属性中。
 * 注意可以在父BeanFactory定义扩展点，在子BeanFactory中定义扩展。
 * </pre>
 * 
 * @author liuwei
 * @version 1.0
 */
public class ExtendPointNodeIterpreter extends AbstractNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		List extendList = new ArrayList();
		addExtends(valueString, extendList, beanFactory);
		
		if(node.getChildren().size()==1){
			Node c =node.getChildren().get(0);
			Class cls =(Class)beanFactory.getBean(c);
			return extendList.toArray((Object[]) Array.newInstance(cls, extendList.size()));
		}
		
		
		return extendList;
	}

	private void addExtends(String patten, List extendList, BeanFactory bf) {
		extendList.addAll(bf.getBeansByPatten(patten, null));
		if (bf instanceof DefaultBeanFactory) {
			List<BeanFactory> bfs = ((DefaultBeanFactory) bf)
					.getChildren();
			for (BeanFactory c : bfs) {
				addExtends(patten, extendList, c);
			}
		}
	}

}
