package com.star.common.richclient.utils.tree;

import java.util.List;

/**
 * ±í²Ù×÷Æ÷.
 * @author liuwei
 *
 */
public interface TreeHandler<T> {

	void add(Object parent, Object child);

	void add(Object parent, List<Object> children);

	void remove(Object o);

	void remove(List<Object> list);

	void refresh(Object o);

	T getSelected();

	List<T> getSelectedList();

	void setSelected(Object o);

	void setSelectedList(List list);

	List<T> getCheckedList();

	void setCheckedList(List list);
	
}
