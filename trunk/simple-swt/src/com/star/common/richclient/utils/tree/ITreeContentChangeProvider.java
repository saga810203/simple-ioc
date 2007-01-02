package com.star.common.richclient.utils.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;

public interface ITreeContentChangeProvider extends ITreeContentProvider{
	void add(Object parent, Object child);
	
	void remove(Object o);
}
