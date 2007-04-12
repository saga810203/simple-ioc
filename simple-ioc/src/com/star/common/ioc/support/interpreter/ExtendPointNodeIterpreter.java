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
 * ��չ��ע�롣�磺
 * 
 * <pre>
 *  a :	com.myace.Parent
 *  	-children : (extend-point)ep.*
 *  ep1  : (string)abc 
 *  ep2  : (string)def
 * <br>
 * �ԡ�ep��Ϊǰ׺��bean���ϲ�Ϊһ��List���󲢱����õ�a��children�����С�
 * ע������ڸ�BeanFactory������չ�㣬����BeanFactory�ж�����չ��
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
