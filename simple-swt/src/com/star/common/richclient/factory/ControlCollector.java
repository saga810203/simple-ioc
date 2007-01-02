package com.star.common.richclient.factory;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Control;

/**
 * 用于控件收集的ControlFactory创建监听器。
 * @author liuwei
 */
public class ControlCollector implements ControlCreateListener{

	private Map<String, Control> collector = new HashMap<String, Control>();
	
	public Map<String, Control> getCollector() {
		return collector;
	}
	
	public void preCreate(IControlFactory factory) {
	}

	public void postCreate(IControlFactory factory, Control control) {
		if(factory.getId()!=null){
			collector.put(factory.getId(),control);
		}
	}

	public Control get(Object key) {
		return collector.get(key);
	}

	public boolean filte(IControlFactory factory) {
		return true;
	}
	
}
