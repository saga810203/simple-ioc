package com.star.common.richclient.interpreter;

import java.net.URL;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.support.interpreter.AbstractNodeInterpreter;
import com.star.common.ioc.support.interpreter.BasicNodeInterpreters.ClassNodeInterpreter;
import com.star.common.util.Node;

public class ImageNodeInterpreter extends AbstractNodeInterpreter {

	private ImageRegistry imageRegistry;

	public void setImageRegistry(ImageRegistry imageRegistry) {
		this.imageRegistry = imageRegistry;
	}

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		if ("image".equals(interpreterName)) {
			return imageRegistry.get(valueString);
		} else {
			return imageRegistry.getDescriptor(valueString);
		}
	}

	public static class ImageRegistryNodeInterpreter extends
			AbstractNodeInterpreter {

		@Override
		protected Object doCreateObject(Node node, String valueString,
				BeanFactory beanFactory, Map<Object, Object> context,
				String interpreterName) throws Exception {
			ImageRegistry r = new ImageRegistry();
			Class location = null;
			if (valueString != null && !"".equals(valueString))
				location = ClassNodeInterpreter.getClassForName(valueString,
						beanFactory);
			for (Node c : node.getChildren()) {
				Object o = beanFactory.getBean(c.getValue());
				ImageDescriptor d = null;
				if (o instanceof URL)
					d = ImageDescriptor.createFromURL((URL) o);
				else
					d = ImageDescriptor.createFromFile(location, (String) o);
				r.put(c.getKey(), d);
			}
			return r;
		}

	}
}
