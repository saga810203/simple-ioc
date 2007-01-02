package com.star.common.richclient.utils.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.Viewer;

import com.star.common.util.bean.PropertyUtils;

public class TreeContentProviderByParent implements ITreeContentChangeProvider{

	private Collection<Object> nodes;
	
	private Map<Class, String> parentPropertyNameMap;
	
	public TreeContentProviderByParent(){}
	
	public TreeContentProviderByParent( Map<Class, String> parentPropertyNameMap){
		this.parentPropertyNameMap=parentPropertyNameMap;
	}
	
	public Object[] getChildren(Object parentElement) {
		List<Object> children = new ArrayList<Object>();
		
		for(Object node : nodes){
			String propName =parentPropertyNameMap.get(node.getClass());
			Object parentNode = PropertyUtils.getNestedProperty(node,propName);
			if(parentElement.equals(parentNode)){
				children.add(propName);
			}
		}
		return children.toArray();
	}

	public Object getParent(Object element) {
		String propName =parentPropertyNameMap.get(element.getClass());
		Object parentNode = PropertyUtils.getNestedProperty(element,propName);
		return parentNode;
	}

	public boolean hasChildren(Object element) {
		for(Object node : nodes){
			String propName =parentPropertyNameMap.get(node.getClass());
			Object parentNode = PropertyUtils.getNestedProperty(node,propName);
			if(element.equals(parentNode)){
				return true;
			}
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		List<Object> roots = new ArrayList<Object>();
		
		for(Object node : (Collection)inputElement){
			String propName =parentPropertyNameMap.get(node.getClass());
			Object parentNode = PropertyUtils.getNestedProperty(node,propName);
			if(parentNode==null){
				roots.add(node);
			}
		}
		return roots.toArray();
	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		nodes = (Collection<Object>) newInput;
	}

	public void dispose() {
		
	}
	
	public void setParentPropertyNameMap(Map<Class, String> parentPropertyNameMap) {
		this.parentPropertyNameMap = parentPropertyNameMap;
	}
		
	//========================
	
	public void add(Object parent, Object child) {
		String propName =parentPropertyNameMap.get(child.getClass());
		PropertyUtils.setNestedProperty(child,propName,parent);
		nodes.add(child);
	}
	
	public void remove(Object o) {
		nodes.remove(o);
		
	}
	
}
