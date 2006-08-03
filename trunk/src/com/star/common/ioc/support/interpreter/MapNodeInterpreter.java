package com.star.common.ioc.support.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.Node;

/**
 * Map�Ľڵ���������磺
 * <pre>
 * a= (map)java.util.TreeMap
 * 	-(int)1=(string)x
 *  -(int)2=(string)y
 * </pre>
 * "(map)"���������Ҳ����ʡ�ԣ���ʱʹ��HashMap��
 * 
 * @author liuwei
 * @version 1.0
 */
public class MapNodeInterpreter extends AbstractNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Class cls = null;
		if("".equals(valueString)){
			cls = HashMap.class;
		}else{
			cls = BasicNodeInterpreters.ClassNodeInterpreter.getClassForName(valueString,beanFactory);
		}
		Map result = (Map)cls.newInstance();
		
		for(Node n: node.getChildren()){
			Object k = beanFactory.getBean(n.getKey(),context);
			Object v = beanFactory.getBean(n,context);
			result.put(k,v);
		}
		
		return result;
	}

}
