package com.star.common.ioc.support.interpreter;

import java.beans.PropertyDescriptor;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.Node;
import com.star.common.util.bean.PropertyUtils;

/**
 * 按照属性名自动属性注入节点解析器。如：a=(auto)com.star.A
 * @author liuwei
 * @version 1.0
 */
public class AutoInjectNodeInterpreter extends AbstractNodeInterpreter {
		
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context, String interpreterName) throws Exception{
		Object result =  beanFactory.getBean(node, valueString, context);
		
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(result);
		for(PropertyDescriptor p: pds){
			if(p.getWriteMethod()!=null && beanFactory.containsBean(p.getName()) && node.findChildByKey(p.getName())==null){
					Object o = beanFactory.getBean(p.getName(),context);
					PropertyUtils.setProperty(result,p.getName(),o);
			}
		}
		return result;
		
	}
}	