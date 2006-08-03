package com.star.common.ioc.support.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.Node;

/**
 * List�Ľڵ���������磺
 * <pre>
 * a		= (list)java.util.LinkedList
 * 	-=(int)10
 *  -=(int)20
 * </pre>
 * "(list)"���������Ҳ����ʡ�ԣ���ʱʹ��ArrayList��
 * 
 * @author liuwei
 * @version 1.0
 */
public class ListNodeInterpreter extends AbstractNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Class cls = null;
		if("".equals(valueString)){
			cls = ArrayList.class;
		}else{
			cls = BasicNodeInterpreters.ClassNodeInterpreter.getClassForName(valueString,beanFactory);
		}
		List result = (List)cls.newInstance();
		
		for(Node n: node.getChildren()){
			Object item = beanFactory.getBean(n,context);
			result.add(item);
		}
		
		return result;
	}

}
