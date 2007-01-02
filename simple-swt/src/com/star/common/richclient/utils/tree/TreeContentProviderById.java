package com.star.common.richclient.utils.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;

import com.star.common.util.bean.PropertyUtils;

public class TreeContentProviderById implements ITreeContentChangeProvider {

	private Collection nodes;

	private String idPropertyName;

	private String parentIdPropertyName;

	public TreeContentProviderById() {
	}

	public TreeContentProviderById(String idPropertyName,
			String parentIdPropertyName) {
		this.idPropertyName = idPropertyName;
		this.parentIdPropertyName = parentIdPropertyName;
	}

	public Object[] getChildren(Object parentElement) {
		Object id = PropertyUtils.getNestedProperty(parentElement,
				idPropertyName);

		List<Object> children = new ArrayList<Object>();

		for (Object node : nodes) {
			Object parentId = PropertyUtils.getNestedProperty(node,
					parentIdPropertyName);
			if (id.equals(parentId)) {
				children.add(node);
			}
		}
		return children.toArray();
	}

	public Object getParent(Object element) {
		Object parentId = PropertyUtils.getNestedProperty(element,
				parentIdPropertyName);
		if (parentId != null) {
			for (Object node : nodes) {
				Object id = PropertyUtils.getNestedProperty(node,
						idPropertyName);
				if (parentId.equals(id)) {
					return node;
				}
			}
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		Object id = PropertyUtils.getNestedProperty(element, idPropertyName);

		for (Object node : nodes) {
			Object parentId = PropertyUtils.getNestedProperty(node,
					parentIdPropertyName);
			if (id.equals(parentId)) {
				return true;
			}
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		List<Object> roots = new ArrayList<Object>();
		for (Object node : (Collection) inputElement) {
			Object parentId = PropertyUtils.getNestedProperty(node,
					parentIdPropertyName);
			if (parentId == null) {
				roots.add(node);
			}
		}
		return roots.toArray();
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		nodes = (Collection) newInput;
	}

	public void dispose() {

	}

	public void setIdPropertyName(String idPropertyName) {
		this.idPropertyName = idPropertyName;
	}

	public void setParentIdPropertyName(String parentIdPropertyName) {
		this.parentIdPropertyName = parentIdPropertyName;
	}

	// ==============================
	@SuppressWarnings("unchecked")
	public void add(Object parent, Object child) {
		if (parent != null) {
			Object parentId = PropertyUtils.getNestedProperty(parent,
					idPropertyName);
			PropertyUtils.setNestedProperty(child, parentIdPropertyName,
					parentId);
		}
		nodes.add(child);
	}

	public void remove(Object o) {
		nodes.remove(o);

	}

}