package com.star.common.richclient.utils.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;

import com.star.common.util.bean.PropertyUtils;

/**
 * 内容器。由它决定那些对象记录应该输出在TreeViewer里显示
 */
public class TreeContentProviderByChildren implements
		ITreeContentChangeProvider {

	private String childrenPropertyName = "children";

	// private Map<Class, String[]> childrenPropertyNameMap;

	public TreeContentProviderByChildren() {
	}

	public TreeContentProviderByChildren(String childrenPropertyName) {
		if (childrenPropertyName != null)
			this.childrenPropertyName = childrenPropertyName;
	}

	/**
	 * 由这个方法决定树的一级显示那些对象
	 * 
	 * @param inputElement
	 *            是用tv.setInput()方法输入的那个对象
	 * @return Object[]一个数组，数组中一个元素就是一个结点
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Object[])
			return (Object[]) inputElement;
		if (inputElement instanceof Collection)
			return ((Collection) inputElement).toArray();
		if (inputElement != null)
			return getChildren(inputElement);
		return new Object[0];
	}

	/**
	 * 判断某结点是否有子结点。如果有子结点，这时结点前都有一个“＋”号图标
	 * 
	 * @param element
	 *            需要判断是否有子的结点
	 * @return true有子结点，false无子结点
	 */
	public boolean hasChildren(Object element) {
		List list = null;
		try {
			list = (List) PropertyUtils.getProperty(element,
					childrenPropertyName);
		} catch (Exception e) {

		}
		if (list == null || list.isEmpty()) // 判断是否有子结点
			return false;
		else
			return true;
	}

	/**
	 * 由这个方法决定父结点应该显示那些子结点。
	 * 
	 * @param parentElement
	 *            当前被点击的结点对象
	 * @return 由子结点做为元素的数组
	 */
	public Object[] getChildren(Object parentElement) {
		List list = (List) PropertyUtils.getProperty(parentElement,
				childrenPropertyName);
		if (list == null || list.isEmpty())
			return new Object[0];
		else
			return list.toArray();
	}

	public String getChildrenPropertyName() {
		return childrenPropertyName;
	}

	public void setChildrenPropertyName(String propertyName) {
		this.childrenPropertyName = propertyName;
	}

	// --------------以下方法无用，空实现----------------
	public Object getParent(Object element) {
		return null;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@SuppressWarnings("unchecked")
	public void add(Object parent, Object child) {
		List list = (List) PropertyUtils.getProperty(parent,
				childrenPropertyName);
		if (list == null) {
			list = new ArrayList();
			PropertyUtils.setProperty(parent, childrenPropertyName, list);
		}
		list.add(child);
	}

	public void remove(Object o) {
		// TODO Auto-generated method stub

	}
}