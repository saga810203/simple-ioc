package com.star.common.ioc.support.interpreter;

import java.lang.reflect.Array;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.Node;

/**
 * 数组的节点解析器。如：
 * <pre>
 * a		= (array)java.lang.String
 * 	-=(string)10
 *  -=(string)20
 *  
 * b		= (array)int
 * 	-=(int)10
 *  -=(int)20
 * </pre>
 * "(list)"后面的类型也可以省略，此时使用ArrayList。
 * 
 * @author liuwei
 * @version 1.0
 */
public class ArrayNodeInterpreter extends AbstractNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Class cls = null;
		if("".equals(valueString)){
			cls = Object.class;
		}else{
			cls = BasicNodeInterpreters.ClassNodeInterpreter.getClassForName(valueString,beanFactory);
		}
 
		Object result =Array.newInstance(cls,node.getChildren().size());
		
		for(int i=0;i< node.getChildren().size();i++){
			Node n = node.getChildren().get(i);
			Object item = beanFactory.getBean(n,context);
			Array.set(result,i,item);
		}
		
		return result;
	}

}
