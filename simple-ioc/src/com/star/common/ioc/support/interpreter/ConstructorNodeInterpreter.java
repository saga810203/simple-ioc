package com.star.common.ioc.support.interpreter;

import java.lang.reflect.Constructor;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.DefaultBeanFactory;
import com.star.common.util.ClassUtils;
import com.star.common.util.Node;
/**
 * 实现构造体注入。如：
 * <pre>
 * a :	(constructor)java.lang.String
 * 	-(constructor)
 * 		--=(string)"abc"
 * <br>对于构造体的参数Node：键为参数类型，值为参数的值。如果参数类型为空，则取参数的class代替，此时参数不能为空。
 * </pre>
 * 
 * @author liuwei
 * @version 1.0
 */
public class ConstructorNodeInterpreter extends BeanNodeInterpreter {
	
	protected Object createInstance(Class clazz, Node node, BeanFactory beanFactory, Map<Object, Object> context) throws Exception {
		Constructor constructor = null;
		Object[] argArray = null;

		//TODO 这里需要斟酌
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
