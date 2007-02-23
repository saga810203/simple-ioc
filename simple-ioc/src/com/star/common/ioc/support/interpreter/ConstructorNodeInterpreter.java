package com.star.common.ioc.support.interpreter;

import java.lang.reflect.Constructor;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.ClassUtils;
import com.star.common.util.Node;
/**
 * ʵ�ֹ�����ע�롣�磺
 * <pre>
 * a :	(constructor)java.lang.String
 * 	-(constructor)
 * 		--=(string)"abc"
 * <br>���ڹ�����Ĳ���Node����Ϊ�������ͣ�ֵΪ������ֵ�������������Ϊ�գ���ȡ������class���棬��ʱ��������Ϊ�ա�
 * </pre>
 * 
 * @author liuwei
 * @version 1.0
 */
public class ConstructorNodeInterpreter extends BeanNodeInterpreter {
	
	protected Object createInstance(Class clazz, Node node, BeanFactory beanFactory, Map<Object, Object> context) throws Exception {
		Constructor constructor = null;
		Object[] argArray = null;

		//TODO ������Ҫ����
		for (Node child : node.getChildren()) {
			if(child.getKey()!=null && child.getKey().startsWith("(constructor)")){
				DefaultBeanFactory dbf =((DefaultBeanFactory)beanFactory);
				if(dbf.containsCacheBean(child)){
					constructor = (Constructor)dbf.getCacheBean(child);
					argArray = new Object[child.getChildren().size()];
					for (int i=0;i<child.getChildren().size();i++) {
						Node n = child.getChildren().get(i);
						if (BEAN_ID.equals(n.getValue())) {
							argArray[i]=node.getKeyPath();
						} 
						else{
							argArray[i]= beanFactory.getBean(n,context);
						}
					}
				} else{
					Class[] classArray = new Class[child.getChildren().size()];
					argArray = new Object[child.getChildren().size()];
					for (int i=0;i<child.getChildren().size();i++) {
						Node n = child.getChildren().get(i);
						if (BEAN_ID.equals(n.getValue())) {
							argArray[i]=node.getKeyPath();
						} 
						else{
							argArray[i]= beanFactory.getBean(n, n.getValue(),null);
						}
						String className = n.getKey();
						if (className != null && !"".equals(className)) {
							classArray[i] = ClassUtils.getClassForName(className);
						} else {
							classArray[i] = argArray[i].getClass();
						}
					}
					constructor = clazz.getConstructor(classArray);
					dbf.putCacheBean(child, constructor);
				}
				break;
			}
		}
		
		Object result = constructor.newInstance(argArray);

		return result;
	}	
}
