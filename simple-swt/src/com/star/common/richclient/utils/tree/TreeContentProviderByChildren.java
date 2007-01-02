package com.star.common.richclient.utils.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;

import com.star.common.util.bean.PropertyUtils;

/**
 * ������������������Щ�����¼Ӧ�������TreeViewer����ʾ
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
	 * �����������������һ����ʾ��Щ����
	 * 
	 * @param inputElement
	 *            ����tv.setInput()����������Ǹ�����
	 * @return Object[]һ�����飬������һ��Ԫ�ؾ���һ�����
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
	 * �ж�ĳ����Ƿ����ӽ�㡣������ӽ�㣬��ʱ���ǰ����һ����������ͼ��
	 * 
	 * @param element
	 *            ��Ҫ�ж��Ƿ����ӵĽ��
	 * @return true���ӽ�㣬false���ӽ��
	 */
	public boolean hasChildren(Object element) {
		List list = null;
		try {
			list = (List) PropertyUtils.getProperty(element,
					childrenPropertyName);
		} catch (Exception e) {

		}
		if (list == null || list.isEmpty()) // �ж��Ƿ����ӽ��
			return false;
		else
			return true;
	}

	/**
	 * ������������������Ӧ����ʾ��Щ�ӽ�㡣
	 * 
	 * @param parentElement
	 *            ��ǰ������Ľ�����
	 * @return ���ӽ����ΪԪ�ص�����
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

	// --------------���·������ã���ʵ��----------------
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