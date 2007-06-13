package com.star.common.richclient.support;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;

import com.star.common.ioc.BeanFactory;
import com.star.common.ioc.BeanFactoryAware;
import com.star.common.util.Node;
import com.star.common.util.TreeMap;

/**
 * 根据传入的Node树，构建MenuBarManager、CoolBarManager、StatusLineManager的工厂。
 * 如果一个Node拥有子Node，则认为它是Menu或者ToolBar；根据它的key+".text"得到的国际化信息作为它的名称。
 * 如果一个Node没有子Node，则认为它是Action；通过它key值找到在BeanFactory里面对应的Bean。
 * 如此对Node树遍历，得到MenuBarManager或者CoolBarManager。
 * 
 * @author liuwei
 * @version 1.0
 * @see com.star.common.util.Node
 * @see com.star.common.util.TreeMap
 */
public class BarManagerFactory implements MenuBarManagerFactroy,
		CoolBarManagerFactory, StatusLineManagerFactory, BeanFactoryAware {

	private static final String EXTEND_POINT_TAG = "(extend-point)";

	private static final String PLACEHOLDER = "<placeholder>";

	private static final String COOL_BAR = "coolBar";

	private static final String MENU_BAR = "menuBar";

	private int mode = 0;

	private String filePath = null;

	private BeanFactory beanFactory;

	protected Node menuBarRoot;

	protected Node coolBarRoot;

	public void init() {
		if (getFilePath() != null) {
			TreeMap map = new TreeMap();
			map.load(getFilePath());

			if (map.containsKey(MENU_BAR)) {
				menuBarRoot = map.get(MENU_BAR);
			}
			if (map.containsKey(COOL_BAR)) {
				coolBarRoot = map.get(COOL_BAR);
			}
		}
	}

	public boolean hasMenuBar() {
		return menuBarRoot != null;
	}

	public boolean hasCoolBar() {
		return coolBarRoot != null;
	}

	public boolean hasStatusLine() {
		return true;
	}

	public MenuManager createMenuBarManager() {
		MenuManager manager = new MenuManager();
		addMenu(menuBarRoot, manager);
		return manager;
	}

	public CoolBarManager createCoolBarManager(int style) {
		CoolBarManager manager = new CoolBarManager(style);
		addToolBar(coolBarRoot, manager, style);
		return manager;
	}

	public StatusLineManager createStatusLineManager() {
		StatusLineManager manager = new StatusLineManager();
		return manager;
	}

	private void addToolBar(Node root, CoolBarManager manager, int style) {
		for (Node child : root.getChildren()) {
			ToolBarManager c2 = new ToolBarManager(style);
			for (Node child2 : child.getChildren()) {
				addAction(c2, child2);
			}
			manager.add(c2);
		}
	}

	private void addMenu(Node node, IContributionManager manager) {
		for (Node child : node.getChildren()) {
			if (child.getChildren().isEmpty()) {
				addAction(manager, child);
			} else {
				MenuManager c2 = new MenuManager(getText(child.getKey(), child
						.getValue(),(BeanFactory)child.getObject()), child.getKey());
				addMenu(child, c2);
				manager.add(c2);
			}
		}
	}

	private void addAction(IContributionManager manager, Node child) {
		boolean stop = false;
		stop = attemptPlaceHolder(manager, child);
		if (stop) {
			return;
		}
		stop = attemptExtendPoint(manager, child);
		if (stop) {
			return;
		}
		stop = attemptSeparator(manager, child);
		if (stop) {
			return;
		}
		stop = attemptAction(manager, child);
		if (stop) {
			return;
		}
	}

	private boolean attemptAction(IContributionManager manager, Node child) {
		Action action = getAction(child.getKey(), child.getValue(),(BeanFactory)child.getObject());
		ActionContributionItem item = new ActionContributionItem(action);
		item.setMode(mode);
		manager.add(item);
		return true;
	}

	private boolean attemptSeparator(IContributionManager manager, Node child) {
		if (child.getKey() == null || "".equals(child.getKey())) {
			manager.add(new Separator());
			return true;
		}
		return false;
	}

	private boolean attemptPlaceHolder(IContributionManager manager, Node child) {
		if (PLACEHOLDER.equals(child.getValue())) {
			return true;
		}
		return false;
	}

	private boolean attemptExtendPoint(IContributionManager manager, Node child) {
		if (child.getValue() != null
				&& child.getValue().startsWith(EXTEND_POINT_TAG)) {
			List<Node> extendNode = beanFactory.getBean(child.getValue(),
					List.class);
			for (Node n : extendNode) {
				addAction(manager, n);
			}
			return true;
		}
		return false;
	}

	// =======================================

	protected Action getAction(String id, String defaultText,BeanFactory beanFactory) {
		Action action = getBeanFactory().containsBean(id) ? beanFactory
				.getBean(id, Action.class) : getDefaultAction(id, defaultText,beanFactory);
		return action;
	}

	protected Action getDefaultAction(String id, String defaultText,BeanFactory beanFactory) {
		Action a = new Action() {
			public void run() {
				notifyResult(true);
			}
		};
		a.setId(id);

		// TODO 设置文字图标
		a.setText(getText(id, defaultText,beanFactory));
		// a.setHoverImageDescriptor();
		// a.setDisabledImageDescriptor();

		return a;
	}

	protected String getText(String id, String defaultText,BeanFactory beanFactory) {
		String text = beanFactory.getBean(
				"(message_can_null)" + id + ".text", String.class);
		return text == null ? defaultText : text;
	}

	// =============================

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void setCoolBarRoot(Node coolBarRoot) {
		this.coolBarRoot = coolBarRoot;
	}

	public void setMenuBarRoot(Node menuBarRoot) {
		this.menuBarRoot = menuBarRoot;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}