package com.star.common.richclient.utils.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * Ä¬ÈÏµÄÊ÷²Ù×÷Æ÷.
 * 
 * @author liuwei
 * 
 */
public class DefaultTreeHandler<T> implements TreeHandler<T> {

	private TreeViewer viewer;

	private ITreeContentChangeProvider contentProvider;

	private List<Object> datas = new ArrayList<Object>();

	private DefaultTreeHandler(TreeViewer viewer) {
		this.viewer = viewer;
		this.contentProvider = (ITreeContentChangeProvider) viewer
				.getContentProvider();
		viewer.setInput(datas);
		viewer.setData("treeHandler", this);
	}

	@SuppressWarnings("unchecked")
	public static <T> TreeHandler<T> getTableHandler(TreeViewer viewer) {
		TreeHandler<T> h = (TreeHandler<T>) viewer.getData("treeHandler");
		if (h == null) {
			h = new DefaultTreeHandler<T>(viewer);
		}
		return h;
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#add(java.lang.Object, java.lang.Object)
	 */
	public void add(Object parent, Object child) {
		contentProvider.add(parent, child);
		viewer.add(parent, child);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#add(java.lang.Object, java.util.List)
	 */
	public void add(Object parent, List<Object> children) {
		for (Object child : children) {
			contentProvider.add(parent, child);
		}
		viewer.add(parent, children.toArray());
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#remove(java.lang.Object)
	 */
	public void remove(Object o) {
		contentProvider.remove(o);
		viewer.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#remove(java.util.List)
	 */
	public void remove(List<Object> list) {
		for (Object o : list) {
			contentProvider.remove(o);
		}
		viewer.remove(list.toArray());
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#refresh(java.lang.Object)
	 */
	public void refresh(Object o) {
		viewer.refresh(o);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#getSelected()
	 */
	@SuppressWarnings("unchecked")
	public T getSelected() {
		return (T) ((StructuredSelection) viewer.getSelection())
				.getFirstElement();
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#getSelectedList()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getSelectedList() {
		return ((StructuredSelection) viewer.getSelection()).toList();
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#setSelected(java.lang.Object)
	 */
	public void setSelected(Object o) {
		StructuredSelection s = o == null ? StructuredSelection.EMPTY
				: new StructuredSelection(o);
		viewer.setSelection(s);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#setSelectedList(java.util.List)
	 */
	public void setSelectedList(List list) {
		StructuredSelection s = list == null ? StructuredSelection.EMPTY
				: new StructuredSelection(list);
		viewer.setSelection(s);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#getCheckedList()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getCheckedList() {
		if (viewer instanceof CheckboxTreeViewer) {
			return (List<T>) Arrays.asList(((CheckboxTreeViewer) viewer)
					.getCheckedElements());
		}
		return new ArrayList<T>(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.tree.TreeHandler#setCheckedList(java.util.List)
	 */
	public void setCheckedList(List list) {
		if (viewer instanceof CheckboxTreeViewer) {
			CheckboxTreeViewer cv = (CheckboxTreeViewer) viewer;
			cv.setCheckedElements(list.toArray());
		}
	}

}
