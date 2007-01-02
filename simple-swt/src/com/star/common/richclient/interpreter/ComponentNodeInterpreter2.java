package com.star.common.richclient.interpreter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Control;

import com.star.common.ioc.BeanFactory;
import com.star.common.richclient.component.ControlMapAware;
import com.star.common.richclient.component.Listener;
import com.star.common.richclient.factory.ControlCollector;
import com.star.common.richclient.factory.IControlFactory;
import com.star.common.util.Node;

public class ComponentNodeInterpreter2 extends
		AbstractControlFactoryNodeInterpreter {

	@Override
	protected Object doCreateObject(Node node, String valueString,
			BeanFactory beanFactory, Map<Object, Object> context,
			String interpreterName) throws Exception {
		Node eventHandlerNode = node.findChildByKey("eventHandler");
		Object eventHandler = beanFactory.getBean(eventHandlerNode);

		Node controlNode = node.findChildByKey("FControl");
		IControlFactory cf = (IControlFactory) beanFactory.getBean(controlNode);
		cf.addCreateListener(new InControlCreateListener(eventHandler));

		return cf;
	}

	private class InControlCreateListener extends ControlCollector {

		private Object eventHandler = null;

		private Map<String, List<Method>> controlEventsMap = new HashMap<String, List<Method>>();

		public InControlCreateListener(Object eventHandler) {
			this.eventHandler = eventHandler;
			if (eventHandler instanceof ControlMapAware) {
				((ControlMapAware) eventHandler).setControlMap(this
						.getCollector());
			}

			for (Method method : eventHandler.getClass().getMethods()) {
				String name = method.getName();
				int index = name.indexOf('_');
				if (index < 0) {
					continue;
				}
				String controlId = name.substring(0, index);
				List<Method> events = null;
				if (controlEventsMap.containsKey(controlId)) {
					events = controlEventsMap.get(controlId);
				} else {
					events = new ArrayList<Method>();
					controlEventsMap.put(controlId, events);
				}
				events.add(method);
			}
		}

		public void preCreate(IControlFactory factory) {
			super.preCreate(factory);
			String id = factory.getId();
			if (id != null && controlEventsMap.containsKey(id)) {
				List<Method> events = controlEventsMap.get(id);
				for (Method event : events) {
					if ("preCreate".equals(event)) {
						try {
							event.invoke(eventHandler, factory);
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}

		public void postCreate(IControlFactory factory, Control control) {
			super.postCreate(factory, control);
			String id = factory.getId();
			if(id == null){return;}
			
			try {
				Field field= eventHandler.getClass().getDeclaredField(id);
				field.setAccessible(true);
				field.set(eventHandler,control);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				;
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
			
			if (controlEventsMap.containsKey(id)) {
				List<Method> events = controlEventsMap.get(id);
				for (Method event : events) {
					if (event.getName().contains("postCreate")) {
						try {
							event.invoke(eventHandler, factory, control);
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException(e);
						}
					} else {
						Listener listenerDef = event
								.getAnnotation(Listener.class);
						if (listenerDef == null) {
							continue;
						}
						
						Object obj = control;
						Method ms = null;
						try {
							ms = control.getClass().getMethod(
									listenerDef.value(),
									listenerDef.paramType());
						} catch (SecurityException e) {
							throw new RuntimeException(e);
						} catch (NoSuchMethodException e) {
							Object viewer = control.getData("viewer");
							if(viewer!=null){
								obj = viewer;
								try {
									ms = viewer.getClass().getMethod(
											listenerDef.value(),
											listenerDef.paramType());
								} catch (SecurityException e1) {
									throw new RuntimeException(e);
								} catch (NoSuchMethodException e1) {
									throw new RuntimeException(e);
								}
							}
							else{
								throw new RuntimeException(e);
							}
							
						}
						Object listener = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
								new Class[] { listenerDef.paramType() },
								new InInvocationHandler(event, eventHandler));
						try {
							ms.invoke(obj, listener);
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
	}

	private class InInvocationHandler implements InvocationHandler {
		private Object eventHandler;

		private Method event;

		public InInvocationHandler(Method event, Object eventHandler) {
			this.event = event;
			this.eventHandler = eventHandler;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if (event.getName().contains(method.getName())) {
				event.invoke(eventHandler, args);
			}
			return null;
		}
	}

}
