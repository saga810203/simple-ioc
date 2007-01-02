package com.star.common.richclient.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.star.common.ioc.BeanFactory;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.factory.composite.CompositeFactory;
import com.star.common.richclient.factory.composite.support.ParameterCompositeFactory;
import com.star.common.util.Node;
import com.star.common.util.StringUtils;
import com.star.common.util.bean.PropertyUtils;

/**
 * <pre>
 *  layout1=(compositeFactory)
 * 	- root 			: .border(model=n)
 * 	  --top			: .border
 * 						---data:(map)
 * 							----(string)a1:(string)abc
 * 	  	---top1		: .ph 
 * 	  	---top2		: (ref)layout2
 * 	  --center		: .border(model=w)
 * 	  	---4		: .ph
 * 	  	---5		: .ph
 * </pre>
 * 
 * @author liuwei
 * @version 1.0
 */
public class CompositeFactoryNodeInterpreter extends AbstractControlFactoryNodeInterpreter {

	private Map<String, Class<IControlFactory>> register = new HashMap<String, Class<IControlFactory>>();

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Node root = node.getChildren().get(0);
		return createNodeObject(root, context, beanFactory);
	}

	private Object createNodeObject(Node node, Map<Object, Object> context,
			BeanFactory beanFactory) throws Exception {

		String value = node.getValue();
		if (value == null || value.length() == 0 || value.charAt(0) != '.') {
			return beanFactory.getBean(node, context);
		} else {
			IControlFactory cf = null;
			String id = node.getKey();
			value = value.substring(1);
			// border(model=n)
			// border(model=n) => [border,model=n]
			// border => [border,null]
			String[] ds = StringUtils.splite2(value);
			String facKey = ds[0];
			String param = ds[1];

			Class<IControlFactory> clazz = register.get(facKey);
			if (clazz == null) {
				throw new IllegalArgumentException("not find funcKey=" + facKey);
			}
			cf = clazz.newInstance();
			cf.setId(id);
			if ((cf instanceof ParameterCompositeFactory) && param != null
					&& param.length() > 0) {
				((ParameterCompositeFactory) cf).setParameters(param);
			}
			List<IControlFactory> ccfList = new ArrayList<IControlFactory>();
			for (Node c : node.getChildren()) {
				Object ccf = createNodeObject(c, context, beanFactory);
				if (cf instanceof CompositeFactory
						&& (ccf == null || ccf instanceof IControlFactory)) {
					ccfList.add((IControlFactory) ccf);
				} else {
					PropertyUtils.setProperty(cf, c.getKey(), ccf);
				}
			}
			
			if(cf instanceof CompositeFactory){
				IControlFactory[] ccfs = new IControlFactory[ccfList.size()];
				((CompositeFactory) cf).setChildren(ccfList.toArray(ccfs));
			}
			return cf;
		}
	}

	public Map<String, Class<IControlFactory>> getRegister() {
		return register;
	}

	public void setRegister(Map<String, Class<IControlFactory>> register) {
		this.register = register;
	}
}