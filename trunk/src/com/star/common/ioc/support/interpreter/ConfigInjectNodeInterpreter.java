package com.star.common.ioc.support.interpreter;

import java.util.HashMap;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.util.Node;
import com.star.common.util.bean.PropertyUtils;

/**
 * �����õ�����ע��ڵ���������磺
 * <pre>@action			:	common..ioc.support.ext.ConfigInjectNodeInterpreter
 * 	-autoConfig	:	(string)id((string)BEAN_ID);text((message)DEFAULT);imageDescriptor((imageDescriptor)DEFAULT)
 * 
 * a			:	(action)com.star.A
 * </pre>
 * ����a��������ע��id��text��imageDescriptor���ԡ�
 * <br>���������е� BEAN_ID����bean��Id������Ϊa��
 * PROP_NAME������������������id��text��imageDescriptor��
 * DEFAULT ���� BEAN_ID.PROP_NAME 
 * @author liuwei
 * @version 1.0
 */
public class ConfigInjectNodeInterpreter extends AbstractNodeInterpreter {
	
	public static final String BEAN_ID = "BEAN_ID";
	
	public static final String PROP_NAME = "PROP_NAME";
	
	public static final String DEFAULT = "DEFAULT";
	
	private Map<String,String> propertyName_ValueString = new HashMap<String,String>();
	
	public void setAutoConfig(Node autoConfig){
		for(Node n : autoConfig.getChildren()){
			propertyName_ValueString.put(n.getKey(),n.getValue());
		}
	}
	
	@Override
	protected Object doCreateObject(Node node, String valueString, BeanFactory beanFactory, Map<Object, Object> context, String interpreterName) throws Exception {
		Object result = beanFactory.getBean(node, valueString, context);
		config(result, node.getKeyPath(), beanFactory,node, context);
		return result;
	}

	public void config(Object result, String keyPath, BeanFactory beanFactory,Node node, Map<Object, Object> context){
		L: for(String propertyName:propertyName_ValueString.keySet()){
			for(Node c:	node.getChildren()){
				if(propertyName.equals(c.getKey())){
					continue L;
				}
			}
			
			String valueString = propertyName_ValueString.get(propertyName);
			valueString = valueString.replace(DEFAULT,keyPath+'.'+propertyName);
			valueString = valueString.replace(BEAN_ID,keyPath);
			valueString = valueString.replace(PROP_NAME,propertyName);
			
			Object propertyValue = beanFactory.getBean(valueString,context);
			try {
				PropertyUtils.setProperty(result,propertyName,propertyValue);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		for(Node c:	node.getChildren()){
			Object propertyValue = beanFactory.getBean(c,context);
			try {
				PropertyUtils.setProperty(result,c.getKey(),propertyValue);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
	}

}
