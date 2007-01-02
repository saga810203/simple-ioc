package com.star.common.richclient.utils.table;

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
/**
 * �������.�ṩ/��װ�Ա�Ļ�������.
 * @author liuwei
 *
 * @param <T> �����еĶ�������
 */
public interface TableHandler<T> {
	
	TableViewer getViewer();
	
	void insert(T o, int index);

	void add(T o);

	void add(List<T> list);

	void remove(T o);

	void remove(List<T> list);

	void replace(T oldObject, T newObject);

	void clear();

	void reset(List<T> list);

	boolean contain(T o);

	List<T> getAll();

	T getSelected();

	void setSelected(T o);
	
	List<T> getSelectedList();
	
	void setSelectedList(List<T> list);

	List<T> getCheckedList();
	
	void setCheckedList(List<T> list);

}