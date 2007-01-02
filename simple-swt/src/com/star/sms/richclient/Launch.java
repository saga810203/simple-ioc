package com.star.sms.richclient;

import static com.star.common.ioc.BeanFactoryUtils.create;
import static com.star.common.ioc.StaticBeanFactory.getBean;
import static com.star.common.ioc.StaticBeanFactory.init;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Display;

import com.star.common.ioc.BeanFactory;

public class Launch {

	public void run() {
		BeanFactory base = create(Launch.class, "bean-config-base.properties");
		init(base, Launch.class, 
				"bean-config-app.properties",
				"bean-config-template.properties",
				"bean-config-customer-view4.properties");

		ApplicationWindow w = getBean("application", ApplicationWindow.class);
		w.open();
	}

	public static void main(String[] args) {
		
		String ldpath = System.getProperty("java.library.path", "");
		System.out.println(ldpath);
		new Launch().run();
		Display.getCurrent().dispose();
	}

}
