package com.star.common.richclient;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import com.star.common.richclient.factory.IControlFactory;
import com.star.common.richclient.support.CoolBarManagerFactory;
import com.star.common.richclient.support.MenuBarManagerFactroy;
import com.star.common.richclient.support.StatusLineManagerFactory;

/**
 * 应用程序对象。
 * 将ApplicationWindow的模板模式创建，修改为通过设置的形式创建。
 * 
 * @author liuwei
 * @version 1.0
 */
public class Application extends ApplicationWindow {

	private MenuBarManagerFactroy menuBarManagerFactroy;
	
	private CoolBarManagerFactory coolBarManagerFactory;

	private StatusLineManagerFactory statusLineManagerFactory;

	private IControlFactory contentsFactory ;
	
	private String text;

	private Image image;

	private Point size;

	private String toolTipText;
	
	public Application() {
		super(null);
		Display.getDefault();
	    setBlockOnOpen(true);
	}
	
	public void init() {
		if (menuBarManagerFactroy!=null /*&& menuBarManagerFactroy.hasMenuBar()*/) {
			addMenuBar();
		}
		if (coolBarManagerFactory!=null /*&& coolBarManagerFactory.hasCoolBar()*/) {
			addCoolBar(SWT.FLAT);
		}
		if (statusLineManagerFactory!=null /*&& statusLineManagerFactory.hasStatusLine()*/){
			addStatusLine();
		}
	}

	@Override
	protected MenuManager createMenuManager() {
		return menuBarManagerFactroy.createMenuBarManager();
	}
	
	@Override
	protected CoolBarManager createCoolBarManager(int style) {
		return coolBarManagerFactory.createCoolBarManager(style);
	}

	@Override
	protected StatusLineManager createStatusLineManager() {
		return statusLineManagerFactory.createStatusLineManager();
	}
	
	@Override
	protected Control createContents(Composite parent) {
		if(text!=null)
		getShell().setText(text);
		if(image!=null)
		getShell().setImage(image);
		if(size!=null)
		getShell().setSize(size);
		if(toolTipText!=null)
		getShell().setToolTipText(toolTipText);
		
		Control control = contentsFactory.createControl(parent);
		
		return control;
	}
	
	// ============================


	public void setCoolBarManagerFactory(CoolBarManagerFactory coolBarManagerFactory) {
		this.coolBarManagerFactory = coolBarManagerFactory;
	}

	public void setMenuBarManagerFactroy(MenuBarManagerFactroy menuBarManagerFactroy) {
		this.menuBarManagerFactroy = menuBarManagerFactroy;
	}

	public void setStatusLineManagerFactory(StatusLineManagerFactory statusLineManagerFactory) {
		this.statusLineManagerFactory = statusLineManagerFactory;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setSize(Point size) {
		this.size = size;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public void setContentsFactory(IControlFactory contentsFactory) {
		this.contentsFactory = contentsFactory;
	}

}
