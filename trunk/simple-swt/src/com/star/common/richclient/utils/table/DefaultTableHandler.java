package com.star.common.richclient.utils.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
/**
 * 默认的表操作器.
 * @author liuwei
 */
public class DefaultTableHandler<T> implements TableHandler<T> {

	private TableViewer viewer;

	private List<T> datas = new ArrayList<T>();

	private DefaultTableHandler(TableViewer tableViewer) {
		this.viewer = tableViewer;
		tableViewer.setInput(datas);
		tableViewer.setData("tableHandler",this);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> TableHandler<T> getTableHandler(TableViewer tableViewer){
		TableHandler<T> h = (TableHandler<T>)tableViewer.getData("tableHandler");
		if(h==null){
			h=new DefaultTableHandler<T>(tableViewer);
		}
		return h;
	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#insert(java.lang.Object, int)
	 */
	public void insert(T o, int index) {
		datas.add(index, o);
		viewer.insert(o, index);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#add(java.lang.Object)
	 */
	public void add(T o) {
		datas.add(o);
		viewer.add(o);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#add(java.util.List)
	 */
	public void add(List<T> list) {
		datas.addAll(list);
		viewer.add(list.toArray());
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#remove(java.lang.Object)
	 */
	public void remove(T o) {
		datas.remove(o);
		viewer.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#remove(java.util.List)
	 */
	public void remove(List<T> list) {
		datas.removeAll(list);
		viewer.remove(list.toArray());
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#replace(java.lang.Object, java.lang.Object)
	 */
	public void replace(T oldObject, T newObject) {
		int index = datas.indexOf(oldObject);
		datas.set(index, newObject);
		viewer.replace(newObject, index);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#clear()
	 */
	public void clear() {
		datas.clear();
		viewer.refresh();
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#reset(java.util.List)
	 */
	public void reset(List<T> list) {
		datas.clear();
		datas.addAll(list);
		viewer.refresh();
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#contain(java.lang.Object)
	 */
	public boolean contain(T o) {
		return datas.indexOf(o) >= 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#getAll()
	 */
	public List<T> getAll() {
		return new ArrayList<T>(datas);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#getSelected()
	 */
	@SuppressWarnings("unchecked")
	public T getSelected() {
		return (T) ((StructuredSelection) viewer.getSelection())
				.getFirstElement();
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#getSelectedList()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getSelectedList() {
		return (List<T>) ((StructuredSelection) viewer.getSelection()).toList();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#setSelected(java.lang.Object)
	 */
	public void setSelected(T o) {
		StructuredSelection s = o == null ? StructuredSelection.EMPTY
				: new StructuredSelection(o);
		viewer.setSelection(s);
	}

	public void setSelectedList(List<T> list) {
		StructuredSelection s = list == null ? StructuredSelection.EMPTY
				: new StructuredSelection(list);
		viewer.setSelection(s);
	}

	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#getCheckedList()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getCheckedList() {
		if (viewer instanceof CheckboxTableViewer) {
			return (List<T>) Arrays.asList(((CheckboxTableViewer) viewer)
					.getCheckedElements());
		}
		return new ArrayList<T>(0);
	}
	/*
	 * (non-Javadoc)
	 * @see com.star.common.richclient.utils.table.TableHandler#setCheckedList(java.util.List)
	 */
	public void setCheckedList(List<T> list) {
		if (viewer instanceof CheckboxTableViewer) {
			CheckboxTableViewer cv = (CheckboxTableViewer) viewer;
			cv.setCheckedElements(list.toArray());
		}
	}

}
